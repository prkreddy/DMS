package com.iiitb.action;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.apache.struts2.interceptor.SessionAware;

import com.db4o.ObjectContainer;
import com.iiitb.util.ConnectionPool;
import com.iiitb.model.User;
import com.iiitb.model.UserGroup;
import com.iiitb.model.UserRole;
import com.iiitb.util.DMSConstants;
import com.opensymphony.xwork2.ActionSupport;

public class SubmitGroupsToUsers extends ActionSupport implements SessionAware{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String groupname;
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
	
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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
		List<UserGroup> grouplist= new ArrayList<UserGroup>();
		grouplist = db.queryByExample(UserGroup.class);
		System.out.println("****"+groupname);
		System.out.println("****"+username);
		UserGroup tmp=null;
		for(UserGroup ug: grouplist)
		{
			if(ug.getName().equals(groupname))
			{
				tmp=ug;
				break;
			}
		}
		for(User u: userlist)
		{
			if(u.getName().equals(username))
			{
				u.setGroup(tmp);
				db.store(u);
				break;
			}
		}
		/*for(User u: userlist)
		{
			System.out.println("Inside");
			System.out.println(u.getName()+"   ");
			if(u.getGroup()!=null)
			System.out.println(u.getGroup().getName());
		}*/
		ConnectionPool.freeConnection(db);
		return SUCCESS;
	}
	
	
	

}
