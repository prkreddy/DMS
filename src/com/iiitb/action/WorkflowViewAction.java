package com.iiitb.action;

import java.io.File;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.db4o.ObjectContainer;
import com.iiitb.dao.DocFragmentDao;
import com.iiitb.model.DocFragment;
import com.iiitb.model.DocFragmentDisplayDetails;
import com.iiitb.model.RoleBasedWorkflow;
import com.iiitb.model.ThreeTuple;
import com.iiitb.model.User;
import com.iiitb.model.UserSpecificWorkflow;
import com.iiitb.model.Workflow;
import com.iiitb.util.ConnectionPool;
import com.iiitb.util.DMSConstants;
import com.opensymphony.xwork2.ActionSupport;

public class WorkflowViewAction extends ActionSupport implements SessionAware
{
	/**
	 * 
	 */
	
	private Map<String, Object> session;
	private User user;
	
	public List<UserSpecificWorkflow> a=new ArrayList<UserSpecificWorkflow>();
	public List<RoleBasedWorkflow> b=new ArrayList<RoleBasedWorkflow>();
	public List<String> us=new ArrayList<String>();
	public List<String> rb=new ArrayList<String>();
	
	


	public Map<String, Object> getSession() {
		return session;
	}

	public String execute()
	{
		this.setUser((User) session.get(DMSConstants.USER_LOGGED_IN));
		if (this.getUser() == null)
			return LOGIN;
		ObjectContainer db = ConnectionPool.getConnection();
		System.out.println("nnn");
		//DocFragmentDao dao = new DocFragmentDao(db); // a(docFragmentDao);
		
		a=db.queryByExample(UserSpecificWorkflow.class);
		b=db.queryByExample(RoleBasedWorkflow.class);
		
		for(UserSpecificWorkflow u:a)
			us.add(u.getName());
		for(RoleBasedWorkflow r:b)
			rb.add(r.getName());
		
		System.out.println(a);
		System.out.println(b);
		
		ConnectionPool.freeConnection(db);
		return SUCCESS;
	}
	
	public String name;
	public String type;
	public InputStream inputStream;
	public String getDetails()
	{
		this.setUser((User) session.get(DMSConstants.USER_LOGGED_IN));
		
		
		
		ObjectContainer db = ConnectionPool.getConnection();
		DocFragmentDao dao = new DocFragmentDao(db);
		
		
		String s;
		s="<h3>Details of Workflow '"+this.name+"'</h3>";
		int i=0;
		if(type.equals("us"))
		{
			s+="<b>Type:</b> User-specific<br /><br />";
			s+="<b>Activities</b><br />";
			UserSpecificWorkflow us=(UserSpecificWorkflow)dao.getWorkflowByName(name);
			for(Map.Entry<String, User> e:us.getActivitySequence().entrySet())
			{
				s+=(++i)+". Activity <b>"+e.getKey()+"</b> to be done by <b>"+e.getValue().getUsername()+"</b><br />";
			}
		}
		else
		{
			s+="<b>Type:</b> Role-based<br /><br />";
			s+="<b>Activities</b><br />";
			RoleBasedWorkflow rb=(RoleBasedWorkflow)dao.getWorkflowByName(name);
			for(Map.Entry<String, ThreeTuple> e:rb.getActivitySequence().entrySet())
			{
				s+=(++i)+". Activity <b>"+e.getKey()+"</b> to be done by any <b>"+e.getValue().getActorCount()+
						"</b> person(s) in the <b>"+e.getValue().getGroup().getName()+"</b> group in the <b>"+e.getValue().getRole().getName()+"</b> role<br />";
			}
		}
		
		inputStream=new StringBufferInputStream(s);
		
		ConnectionPool.freeConnection(db);
		
		return "success";
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
}
