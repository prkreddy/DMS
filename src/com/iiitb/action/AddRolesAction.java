package com.iiitb.action;
import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import com.iiitb.model.User;
import com.iiitb.util.DMSConstants;
import com.opensymphony.xwork2.ActionSupport;

public class AddRolesAction extends ActionSupport implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		
	}

	public String execute()
	{
		//this.setUser((User) session.get(DMSConstants.USER_LOGGED_IN));
		//if (this.getUser() == null)
		//	return LOGIN;
		return SUCCESS;
	}
	
}
