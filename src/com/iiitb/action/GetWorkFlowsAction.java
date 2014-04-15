package com.iiitb.action;

import java.util.List;

import com.db4o.ObjectContainer;
import com.iiitb.dao.WorkFlowDao;
import com.iiitb.util.ConnectionPool;
import com.opensymphony.xwork2.ActionSupport;

public class GetWorkFlowsAction extends ActionSupport
{
	List<String> roleWorkflows;

	List<String> userworkflows;

	public List<String> getRoleWorkflows()
	{
		return roleWorkflows;
	}

	public void setRoleWorkflows(List<String> roleWorkflows)
	{
		this.roleWorkflows = roleWorkflows;
	}

	public List<String> getUserworkflows()
	{
		return userworkflows;
	}

	public void setUserworkflows(List<String> userworkflows)
	{
		this.userworkflows = userworkflows;
	}

	public String getWorkFlows()
	{

		ObjectContainer container = ConnectionPool.getConnection();

		WorkFlowDao dao = new WorkFlowDao();
		dao.setDb(container);

		this.setRoleWorkflows(dao.getRoleBaseWorkFlowsNames());

		this.setUserworkflows(dao.getUserBaseWorkFlowsNames());

		ConnectionPool.freeConnection(container);

		return SUCCESS;
	}
}
