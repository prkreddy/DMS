package com.iiitb.action;

import java.util.List;

import com.db4o.ObjectContainer;
import com.iiitb.dao.WorkFlowDao;
import com.iiitb.model.RoleBasedWorkflow;
import com.iiitb.model.UserSpecificWorkflow;
import com.iiitb.util.ConnectionPool;
import com.opensymphony.xwork2.ActionSupport;

public class DocTypeGetWorkFlowAction extends ActionSupport
{

	List<RoleBasedWorkflow> roleWorkflows;

	public List<RoleBasedWorkflow> getRoleWorkflows()
	{
		return roleWorkflows;
	}

	public void setRoleWorkflows(List<RoleBasedWorkflow> roleWorkflows)
	{
		this.roleWorkflows = roleWorkflows;
	}

	public List<UserSpecificWorkflow> getUserworkflows()
	{
		return userworkflows;
	}

	public void setUserworkflows(List<UserSpecificWorkflow> userworkflows)
	{
		this.userworkflows = userworkflows;
	}

	List<UserSpecificWorkflow> userworkflows;

	public String execute()
	{

		ObjectContainer container = ConnectionPool.getConnection();

		WorkFlowDao dao = new WorkFlowDao();
		dao.setDb(container);

		this.setRoleWorkflows(dao.getRoleBaseWorkFlows());

		this.setUserworkflows(dao.getUserBaseWorkFlows());

		return SUCCESS;
	}

	public String manageDoctypes()
	{
		return SUCCESS;
	}
	
	public String editDocType(){
		return SUCCESS;
	}
	
	public String createDocType(){
		
		return SUCCESS;
	}

}
