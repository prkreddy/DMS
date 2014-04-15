package com.iiitb.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.db4o.ObjectContainer;
import com.iiitb.dao.DocFragmentDao;
import com.iiitb.model.DocFragment;
import com.iiitb.model.DocumentType;
import com.iiitb.model.User;
import com.iiitb.util.ConnectionPool;
import com.iiitb.util.DMSConstants;
import com.opensymphony.xwork2.ActionSupport;

public class DocumentCreateAction extends ActionSupport implements SessionAware
{
	private User user;
	private Map<String, Object> session;
	private List<String> docFrags;
	private List<String> docTypes;

	public String execute()
	{
		this.setUser((User) session.get(DMSConstants.USER_LOGGED_IN));
		if (this.getUser() == null)
			return LOGIN;

		ObjectContainer db = ConnectionPool.getConnection();
		DocFragmentDao dao = new DocFragmentDao(db);
		this.docFrags = new ArrayList<String>();
		for (DocFragment d : dao.getReusableDocFragments(this.getUser().getUsername(),null))
			this.docFrags.add(d.getDocId());
		
		docTypes=new ArrayList<String>();
		docTypes.add("--Select--");
		List<DocumentType> dtl=db.queryByExample(DocumentType.class);
		for(DocumentType dt:dtl)
			docTypes.add(dt.getName());
		
		ConnectionPool.freeConnection(db);
		return SUCCESS;
	}

	public void setSession(Map<String, Object> arg0)
	{
		this.session = arg0;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public List<String> getDocFrags()
	{
		return docFrags;
	}

	public void setDocFrags(List<String> docFrags)
	{
		this.docFrags = docFrags;
	}

	public List<String> getDocTypes() {
		return docTypes;
	}

	public void setDocTypes(List<String> docTypes) {
		this.docTypes = docTypes;
	}
}
