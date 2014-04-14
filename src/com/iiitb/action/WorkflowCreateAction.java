package com.iiitb.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.db4o.ObjectContainer;
import com.iiitb.dao.DocFragmentDao;
import com.iiitb.dao.UserDao;
import com.iiitb.model.DocFragment;
import com.iiitb.model.User;
import com.iiitb.util.ConnectionPool;
import com.iiitb.util.DMSConstants;
import com.opensymphony.xwork2.ActionSupport;

public class WorkflowCreateAction extends ActionSupport implements SessionAware
{
	private User user;
	private Map<String, Object> session;
	private List<String> docFrags;
	
	private String name="";
	private String type="";
	private List<String> users;
	private List<String> activities;
	
	public String execute()
	{
		this.setUser((User) session.get(DMSConstants.USER_LOGGED_IN));
		if (this.getUser() == null)
			return LOGIN;
		
		if(name==null || name.equals(""))
		{
			UserDao userDao=new UserDao();
			users=new ArrayList<String>();
			for(User u:userDao.getAllUsers())
				users.add(u.getUsername());
			return SUCCESS;
		}
		
		ObjectContainer db = ConnectionPool.getConnection();
		DocFragmentDao dao = new DocFragmentDao(db);
		
		ConnectionPool.freeConnection(db);
		return SUCCESS;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

}
