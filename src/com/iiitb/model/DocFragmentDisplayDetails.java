package com.iiitb.model;

public class DocFragmentDisplayDetails
{
	public String getPathName()
	{
		return pathName;
	}

	public void setPathName(String pathName)
	{
		this.pathName = pathName;
	}

	public String getDocId()
	{
		return docId;
	}

	public void setDocId(String docId)
	{
		this.docId = docId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public String getAccess()
	{
		return access;
	}

	public void setAccess(String access)
	{
		this.access = access;
	}

	public String getDateCreated()
	{
		return dateCreated;
	}

	public void setDateCreated(String dateCreated)
	{
		this.dateCreated = dateCreated;
	}

	public String getDateModified()
	{
		return dateModified;
	}

	public void setDateModified(String dateModified)
	{
		this.dateModified = dateModified;
	}

	public String getActor()
	{
		return actor;
	}

	public void setActor(String actor)
	{
		this.actor = actor;
	}

	public String getSize()
	{
		return size;
	}

	public void setSize(String size)
	{
		this.size = size;
	}

	public String pathName;
	public String docId;
	public String name;
	public String version;
	public String access;
	public String dateCreated;
	public String dateModified;
	public String actor;
	public String size;
	public String enableActivityUpdate;

	public String getEnableActivityUpdate()
	{
		return enableActivityUpdate;
	}

	public void setEnableActivityUpdate(String enableActivityUpdate)
	{
		this.enableActivityUpdate = enableActivityUpdate;
	}
}
