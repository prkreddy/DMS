package com.iiitb.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.db4o.ObjectContainer;
import com.iiitb.dao.DocFragmentDao;
import com.iiitb.model.DocFragment;
import com.iiitb.model.User;
import com.iiitb.util.ConnectionPool;
import com.iiitb.util.DMSConstants;

public class FilterAction implements SessionAware
{
	private String documentName="";
	private User user;
	private Map<String, Object> session;
	private InputStream inputStream;
	private String keywords;
	
	public String execute()
	{
		this.setUser((User) session.get(DMSConstants.USER_LOGGED_IN));
		
		System.out.println(keywords);
		String[] keywordArray=keywords.split("[,]");
		
		ObjectContainer db = ConnectionPool.getConnection();
		DocFragmentDao dao = new DocFragmentDao(db);
		if(documentName.trim().equals(""))
			documentName=null;
		List<DocFragment> dfl=dao.getDocFragmentsBasedOnKeywords(user.getUsername(), keywordArray, documentName);
		String s="";
		for(DocFragment df:dfl)
			s+="<option>"+df.getDocId()+"</option>";
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

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
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

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
}
