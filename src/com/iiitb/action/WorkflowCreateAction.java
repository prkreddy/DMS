package com.iiitb.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.db4o.ObjectContainer;
import com.iiitb.dao.DocFragmentDao;
import com.iiitb.dao.UserDao;
import com.iiitb.model.DocFragment;
import com.iiitb.model.RoleBasedWorkflow;
import com.iiitb.model.ThreeTuple;
import com.iiitb.model.User;
import com.iiitb.model.UserGroup;
import com.iiitb.model.UserRole;
import com.iiitb.model.UserSpecificWorkflow;
import com.iiitb.model.Workflow;
import com.iiitb.util.ConnectionPool;
import com.iiitb.util.DMSConstants;
import com.opensymphony.xwork2.ActionSupport;

public class WorkflowCreateAction extends ActionSupport implements SessionAware
{
	private User user;
	private Map<String, Object> session;
	private List<String> docFrags;
	
	private String wfname="";
	private String wftype="";
	private List<String> users;
	private List<String> activities;
	
	private List<String> groups;
	private List<String> roles;
	
	public String execute()
	{
		String ret=SUCCESS;
		
		this.setUser((User) session.get(DMSConstants.USER_LOGGED_IN));
		if (this.getUser() == null)
			return LOGIN;
		
		ObjectContainer db=ConnectionPool.getConnection();
		
		
		if(wfname==null || wfname.equals(""))
		{	
			users=new ArrayList<String>();
			List<User> ul=db.queryByExample(User.class);
			for(User u:ul)
				users.add(u.getUsername());
			
			groups=new ArrayList<String>();
			List<UserGroup> gl=db.queryByExample(UserGroup.class);
			for(UserGroup g:gl)
				groups.add(g.getName());
			
			roles=new ArrayList<String>();
			List<UserRole> rl=db.queryByExample(UserRole.class);
			for(UserRole r:rl)
				roles.add(r.getName());
			
		}
		else
		{
			ret="success1";
			Workflow w=null;
			
			if(wftype.equals("us"))
			{
				Map<String, User> m=new LinkedHashMap<String, User>();
				List<User> ul=db.queryByExample(User.class);
				for(String a:activities)
				{
					String[] sa=a.split("[-]");
					for(User u:ul)
						if(u.getUsername().equals(sa[1]))
						{
							m.put(sa[0], u);
							break;
						}
				}
				w=new UserSpecificWorkflow(wfname, m);
				
			}
			else
			{
				DocFragmentDao dfDao=new DocFragmentDao(db);
				Map<String, ThreeTuple> m=new LinkedHashMap<String, ThreeTuple>();
				for(String a:activities)
				{
					String[] sa=a.split("[-]");
					m.put(sa[0], new ThreeTuple(
							dfDao.getUserGroupByGroupName(sa[1]), 
							dfDao.getUserRoleByRoleName(sa[2]),
							Integer.parseInt(sa[3])));
					
				}
				w=new RoleBasedWorkflow(wfname, m);
				
			}
			
			db.store(w);
			
		}
		
		ConnectionPool.freeConnection(db);
		return ret;
	}

	public void setSession(Map<String, Object> arg0)
	{
		this.session = arg0;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public List<String> getDocFrags()
	{
		return docFrags;
	}

	public void setDocFrags(List<String> docFrags)
	{
		this.docFrags = docFrags;
	}

	public List<String> getActivities() {
		return activities;
	}

	public void setActivities(List<String> activities) {
		this.activities = activities;
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

	public String getWfname() {
		return wfname;
	}

	public void setWfname(String wfname) {
		this.wfname = wfname;
	}

	public String getWftype() {
		return wftype;
	}

	public void setWftype(String wftype) {
		this.wftype = wftype;
	}

	public List<String> getGroups() {
		return groups;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}
