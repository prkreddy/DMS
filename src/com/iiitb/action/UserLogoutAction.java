package com.iiitb.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.db4o.ObjectContainer;
import com.iiitb.model.User;
import com.iiitb.util.DMSConstants;
import com.opensymphony.xwork2.ActionSupport;

public class UserLogoutAction extends ActionSupport implements SessionAware
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8068073410828579208L;

	@Override
	public String execute() throws Exception
	{

		if (session.get(DMSConstants.USER_LOGGED_IN) != null)
		{
			session.remove(DMSConstants.USER_LOGGED_IN);
		}

		return SUCCESS;
	}

	Map<String, Object> session;

	@Override
	public void setSession(Map<String, Object> arg0)
	{
		session = arg0;

	}

}
