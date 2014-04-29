package com.iiitb.action;

import java.util.ArrayList;
import java.util.List;

import com.db4o.ObjectContainer;
import com.iiitb.dao.WorkFlowDao;
import com.iiitb.model.DocumentType;
import com.iiitb.model.Workflow;
import com.iiitb.util.ConnectionPool;
import com.opensymphony.xwork2.ActionSupport;

public class ManageDocTypeAction extends ActionSupport
{

	List<String> docTypes;

	public List<String> getDocTypes()
	{
		return docTypes;
	}

	public void setDocTypes(List<String> docTypes)
	{
		this.docTypes = docTypes;
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

	List<String> workflowsSelected;

	public List<String> getWorkflowsSelected()
	{
		return workflowsSelected;
	}

	public void setWorkflowsSelected(List<String> workflowsSelected)
	{
		this.workflowsSelected = workflowsSelected;
	}

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

		List<DocumentType> dtl = container.queryByExample(DocumentType.class);
		for (DocumentType docType : dtl)
		{
			if (docType.getName().equals(this.getDocTypeName()))
			{
				container.close();
				return "INPUT";
			}
		}

		DocumentType doctype = new DocumentType(this.getDocTypeName());

		Workflow flow = null;

		String workflow[] = null;
		for (String wf : this.getWorkflowsSelected())
		{

			workflow = wf.split("-");

			if ("user".equals(workflow[0]))
			{
				flow = dao.getUserBaseWorkFlow(workflow[1]);
			}
			else if ("role".equals(workflow[0]))
			{
				flow = dao.getRoleBaseWorkFlow(workflow[1]);
			}

			if (flow != null)
			{
				doctype.getWorkflowList().add(flow);
			}

		}

		container.store(doctype);

		ConnectionPool.freeConnection(container);

		return SUCCESS;

	}

	public String getDocumenttypes()
	{
		ObjectContainer container = ConnectionPool.getConnection();
		docTypes = new ArrayList<String>();

		List<DocumentType> dtl = container.queryByExample(DocumentType.class);
		for (DocumentType docType : dtl)
		{
			docTypes.add(docType.getName());
		}

		ConnectionPool.freeConnection(container);
		return SUCCESS;
	}

}
