package com.iiitb.action;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.apache.struts2.interceptor.SessionAware;

import com.db4o.ObjectContainer;
import com.iiitb.util.ConnectionPool;
import com.iiitb.model.User;
import com.iiitb.model.UserRole;
import com.iiitb.util.DMSConstants;
import com.opensymphony.xwork2.ActionSupport;

public class SubmitRolesToUsers extends ActionSupport implements SessionAware{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String rolename;
	private String username;
	private Map<String, Object> session;
	private User user;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Map<String, Object> getSession() {
		return session;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		
	}
	public String execute()
	{
		//this.setUser((User) session.get(DMSConstants.USER_LOGGED_IN));
		//if (this.getUser() == null)
		//	return LOGIN;
		ObjectContainer db = ConnectionPool.getConnection();
		List<User> userlist= new ArrayList<User>();
		userlist = db.queryByExample(User.class);
		List<UserRole> rolelist= new ArrayList<UserRole>();
		rolelist = db.queryByExample(UserRole.class);
		System.out.println("****"+rolename);
		System.out.println("****"+username);
		UserRole tmp=null;
		for(UserRole ur: rolelist)
		{
			if(ur.getName().equals(rolename))
			{
				tmp=ur;
				break;
			}
		}
		for(User u: userlist)
		{
			if(u.getName().equals(username))
			{
				u.setRole(tmp);
				db.store(u);
				break;
			}
		}
		/*for(User u: userlist)
		{
			
			System.out.println(u.getName()+"   "+u.getRole().getName());
		}*/
		
		ConnectionPool.freeConnection(db);
		return SUCCESS;
	}
	
	
	

}
