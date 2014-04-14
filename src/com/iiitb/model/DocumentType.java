package com.iiitb.model;

import java.util.ArrayList;
import java.util.List;

public class DocumentType
{
	public DocumentType(String name)
	{
		super();
		this.name = name;
		
		this.workflowList= new ArrayList<Workflow>();
	}

	String name;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<Workflow> getWorkflowList()
	{
		return workflowList;
	}

	public void setWorkflowList(List<Workflow> workflowList)
	{
		this.workflowList = workflowList;
	}

	List<Workflow> workflowList;
}
