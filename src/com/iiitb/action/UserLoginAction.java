package com.iiitb.action;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.iiitb.dao.UserDao;
import com.iiitb.model.User;
import com.iiitb.util.ConnectionPool;
import com.iiitb.util.DMSConstants;
import com.opensymphony.xwork2.ActionSupport;

public class UserLoginAction extends ActionSupport implements SessionAware
{

	UserDao userDao;
	private static final long serialVersionUID = -6768084391725350321L;
	private String username;
	private String password;

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	@Override
	public String execute() throws Exception
	{

		userDao = new UserDao();

		User userLoggedin = (User) session.get(DMSConstants.USER_LOGGED_IN);

		if (userLoggedin != null)
		{
			return SUCCESS;
		}
		else
		{

			userLoggedin = userDao.getUser(username);

			if (userLoggedin != null)
			{

				if (!password.equals(userLoggedin.getPassword()))
				{
					addFieldError("password", "username, password didn't match");
					return INPUT;

				}
				else
				{
					session.put(DMSConstants.USER_LOGGED_IN, userLoggedin);
				}

			}
			else
			{
				addFieldError("username", "user doesn't exists");
				return INPUT;
			}
		}
		return SUCCESS;
	}

	@Override
	public void validate()
	{
		if (session.get(DMSConstants.USER_LOGGED_IN) == null)
		{

			if (StringUtils.isEmpty(username) || username == null)
			{
				addFieldError("username", "enter valid username");
			}

			if (StringUtils.isEmpty(password) || password == null)
			{
				addFieldError("password", "enter valid password");
			}
		}

	}

	Map<String, Object> session;

	@Override
	public void setSession(Map<String, Object> arg0)
	{
		session = arg0;

	}

}
