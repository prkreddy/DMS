package com.iiitb.model;

import java.util.Date;

public class RevisionRecord
{


	String userAction;

	String comments;

	Date creationDate;

	String version;

	Date modificationDate;
	

	public String getUserAction()
	{
		return userAction;
	}

	public void setUserAction(String userAction)
	{
		this.userAction = userAction;
	}

	public String getComments()
	{
		return comments;
	}

	public void setComments(String comments)
	{
		this.comments = comments;
	}

	public Date getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(Date creationDate)
	{
		this.creationDate = creationDate;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public Date getModificationDate()
	{
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate)
	{
		this.modificationDate = modificationDate;
	}


}
