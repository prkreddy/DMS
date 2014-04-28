package com.iiitb.action;

import java.util.ArrayList;
import java.util.List;

import com.db4o.ObjectContainer;
import com.iiitb.dao.DocTypeDao;
import com.iiitb.dao.WorkFlowDao;
import com.iiitb.model.DocumentType;
import com.iiitb.model.RoleBasedWorkflow;
import com.iiitb.model.UserSpecificWorkflow;
import com.iiitb.model.Workflow;
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

	String docTypeName;

	public String getDocTypeName()
	{
		return docTypeName;
	}

	public void setDocTypeName(String docTypeName)
	{
		this.docTypeName = docTypeName;
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

	public String getDocWorkFlows()
	{

		ObjectContainer container = ConnectionPool.getConnection();

		DocTypeDao dao = new DocTypeDao();
		dao.setDb(container);

		DocumentType type = dao.getDocType(this.getDocTypeName());

		if (type != null)
		{

			for (Workflow wf : type.getWorkflowList())
			{

				if (wf instanceof RoleBasedWorkflow)
				{
					if (this.getRoleWorkflows() == null)
					{
						this.setRoleWorkflows(new ArrayList<String>());
					}
					this.getRoleWorkflows().add(wf.getName());
				}
				else if (wf instanceof UserSpecificWorkflow)
				{
					if (this.getUserworkflows() == null)
					{
						this.setUserworkflows(new ArrayList<String>());
					}
					this.getUserworkflows().add(wf.getName());
				}

			}

		}
		else
		{
			ConnectionPool.freeConnection(container);
			return "INPUT";
		}
		ConnectionPool.freeConnection(container);

		return SUCCESS;
	}

}
