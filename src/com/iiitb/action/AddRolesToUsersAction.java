package com.iiitb.action;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

import org.apache.struts2.interceptor.SessionAware;

import com.db4o.ObjectContainer;
import com.iiitb.util.ConnectionPool;
import com.iiitb.model.User;
import com.iiitb.model.UserRole;
import com.iiitb.util.DMSConstants;
import com.opensymphony.xwork2.ActionSupport;

public class AddRolesToUsersAction extends ActionSupport implements SessionAware
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	private User user;
	private List<String> roles=new ArrayList<String>();
	private List<String> usernames=new ArrayList<String>();
	public List<String> getUsernames() {
		return usernames;
	}
	public void setUsernames(List<String> usernames) {
		this.usernames = usernames;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public List<String> getUsers() {
		return users;
	}
	public void setUsers(List<String> users) {
		this.users = users;
	}
	public Map<String, Object> getSession() {
		return session;
	}
	private List<String> users;
	public String execute()
	{
		//this.setUser((User) session.get(DMSConstants.USER_LOGGED_IN));
		//if (this.getUser() == null)
		//	return LOGIN;
		ObjectContainer db = ConnectionPool.getConnection();
		List<User> userlist = db.queryByExample(User.class);
		for(User u:userlist)
		{
			usernames.add(u.getName());
		}
		List<UserRole> rolelist = db.queryByExample(UserRole.class);
		for(UserRole ur:rolelist)
		{
			roles.add(ur.getName());
		}	
		ConnectionPool.freeConnection(db);
		return SUCCESS;
	}
	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		
	}
}
