package com.iiitb.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.db4o.ObjectContainer;
import com.iiitb.util.ConnectionPool;
import com.iiitb.model.User;
import com.iiitb.model.UserRole;
import com.iiitb.util.DMSConstants;
import com.opensymphony.xwork2.ActionSupport;

public class SubmitRolesAction extends ActionSupport implements SessionAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	private User user;
	private String rolename;
	public User getUser() {
		return user;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public String execute()
	{
		//this.setUser((User) session.get(DMSConstants.USER_LOGGED_IN));
		//if (this.getUser() == null)
		//	return LOGIN;
		ObjectContainer db = ConnectionPool.getConnection();
		UserRole ur = new UserRole(rolename);
		db.store(ur);
		ConnectionPool.freeConnection(db);
		return SUCCESS;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
