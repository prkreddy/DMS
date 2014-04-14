package com.iiitb.model;

import java.util.Date;

public class User
{
	private UserRole role;
	
	private UserGroup group;
	
	private String name;

	private String username;

	private String password;

	private String email;

	private Date lastLoginDate;


	public User(String name, String username, String password, String email, Date lastLoginDate)
	{
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.email = email;
		this.lastLoginDate = lastLoginDate;
	}

	public Date getLastLoginDate()
	{
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate)
	{
		this.lastLoginDate = lastLoginDate;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

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

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public UserGroup getGroup() {
		return group;
	}

	public void setGroup(UserGroup group) {
		this.group = group;
	}

}
