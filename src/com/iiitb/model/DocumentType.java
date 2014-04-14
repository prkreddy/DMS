package com.iiitb.model;

import java.util.List;

public class DocumentType
{
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
