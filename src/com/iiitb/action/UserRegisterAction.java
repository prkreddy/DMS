package com.iiitb.action;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.iiitb.dao.UserDao;
import com.iiitb.model.User;
import com.iiitb.util.DMSConstants;
import com.opensymphony.xwork2.ActionSupport;

public class UserRegisterAction extends ActionSupport implements SessionAware
{

	UserDao userDao;
	/**
	 * 
	 */
	private static final long serialVersionUID = -6768084391725350321L;
	private String username;
	private String password;
	private String confirmPassword;
	private String fullName;
	private String emailid;

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

	public String getConfirmPassword()
	{
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword)
	{
		this.confirmPassword = confirmPassword;
	}

	public String getFullName()
	{
		return fullName;
	}

	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}

	public String getEmailid()
	{
		return emailid;
	}

	public void setEmailid(String emailid)
	{
		this.emailid = emailid;
	}

	@Override
	public String execute() throws Exception
	{

		userDao = new UserDao();

		User user = userDao.storeUser(fullName, username, confirmPassword, emailid);

		if (user == null)
		{

			return ERROR;
		}
		else
		{

			session.put(DMSConstants.USER_LOGGED_IN, user);
		}

		userDao.listAllUsers();

		return SUCCESS;
	}

	@Override
	public void validate()
	{
		userDao = new UserDao();
		if (StringUtils.isEmpty(username) || username == null)
		{
			addFieldError("username", "enter valid username");
		}

		if (StringUtils.isEmpty(password) || password == null)
		{
			addFieldError("password", "enter valid password");
		}

		if (StringUtils.isEmpty(confirmPassword) || confirmPassword == null)
		{
			addFieldError("confirmPassword", "enter valid confirmPassword");
		}

		if (password != null && confirmPassword != null && !confirmPassword.equals(password))
		{
			addFieldError("confirmPassword", "password and confirmPassword didn't match");
		}

		if (StringUtils.isEmpty(fullName) || fullName == null)
		{
			addFieldError("fullName", "enter valid fullName");
		}

		if (StringUtils.isEmpty(emailid) || emailid == null)
		{
			addFieldError("emailid", "enter valid emailid");
		}

		if (userDao.isUserNameExists(username))
		{
			addFieldError("username", "username already exists");
		}

	}

	Map<String, Object> session;

	@Override
	public void setSession(Map<String, Object> arg0)
	{
		session = arg0;

	}

}
