package com.iiitb.action;

import java.util.List;

import com.db4o.ObjectContainer;
import com.iiitb.dao.WorkFlowDao;
import com.iiitb.model.DocumentType;
import com.iiitb.model.Workflow;
import com.iiitb.util.ConnectionPool;
import com.opensymphony.xwork2.ActionSupport;

public class ManageDocTypeAction extends ActionSupport
{

	String docTypeName;

	public String getDocTypeName()
	{
		return docTypeName;
	}

	public void setDocTypeName(String docTypeName)
	{
		this.docTypeName = docTypeName;
	}

	List<String> roleWorkflows;

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

	List<String> userworkflows;

	public String manageDoctypes()
	{
		return SUCCESS;
	}

	public String editDocType()
	{
		return SUCCESS;
	}

	public String createDocType()
	{

		ObjectContainer container = ConnectionPool.getConnection();

		WorkFlowDao dao = new WorkFlowDao();
		dao.setDb(container);

		DocumentType doctype = new DocumentType(this.getDocTypeName());

		Workflow flow = null;
		for (String roleWf : this.getRoleWorkflows())
		{
			flow = dao.getRoleBaseWorkFlow(roleWf);
			if (flow != null)
			{
				doctype.getWorkflowList().add(flow);
			}
		}

		for (String userWf : this.getUserworkflows())
		{
			flow = dao.getUserBaseWorkFlow(userWf);
			if (flow != null)
			{
				doctype.getWorkflowList().add(flow);
			}
		}

		container.store(doctype);

		ConnectionPool.freeConnection(container);

		return SUCCESS;

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
