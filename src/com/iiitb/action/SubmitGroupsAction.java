package com.iiitb.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.db4o.ObjectContainer;
import com.iiitb.util.ConnectionPool;
import com.iiitb.model.User;
import com.iiitb.model.UserGroup;
import com.iiitb.util.DMSConstants;
import com.opensymphony.xwork2.ActionSupport;

public class SubmitGroupsAction extends ActionSupport implements SessionAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	private User user;
	private String groupname;
	public User getUser() {
		return user;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
		UserGroup ug = new UserGroup(groupname);
		db.store(ug);
		ConnectionPool.freeConnection(db);
		return SUCCESS;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
