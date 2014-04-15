package com.iiitb.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.db4o.ObjectContainer;
import com.iiitb.dao.DocFragmentDao;
import com.iiitb.model.DocFragment;
import com.iiitb.model.DocumentType;
import com.iiitb.model.User;
import com.iiitb.model.Workflow;
import com.iiitb.util.ConnectionPool;
import com.iiitb.util.DMSConstants;

public class PopulateWorkflowListAction implements SessionAware
{
	private String documentType;
	private User user;
	private Map<String, Object> session;
	private InputStream inputStream;
	
	public String execute()
	{
		this.setUser((User) session.get(DMSConstants.USER_LOGGED_IN));
		
		ObjectContainer db = ConnectionPool.getConnection();
		DocFragmentDao dao = new DocFragmentDao(db);
		
		String s="";
		List<DocumentType> dtl=db.queryByExample(DocumentType.class);
		
		for(DocumentType dt:dtl)
			if(dt.getName().equals(documentType))
			{
				for(Workflow w:dt.getWorkflowList())
					s+="<option>"+w.getName()+"</option>";
				break;
			}
		inputStream=new StringBufferInputStream(s);
		
		ConnectionPool.freeConnection(db);
		
		return "success";
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
}
