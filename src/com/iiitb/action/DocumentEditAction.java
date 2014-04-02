package com.iiitb.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.db4o.ObjectContainer;
import com.iiitb.dao.DocFragmentDao;
import com.iiitb.model.DocFragment;
import com.iiitb.model.DocFragmentInfo;
import com.iiitb.model.User;
import com.iiitb.util.ConnectionPool;
import com.iiitb.util.DMSConstants;
import com.opensymphony.xwork2.ActionSupport;

public class DocumentEditAction extends ActionSupport implements SessionAware
{
	private User user;
	private Map<String, Object> session;
	private List<String> docFrags1;
	private List<String> docFrags2;
	private List<String> docFragsBeforeNativeContent;
	private List<String> docFragsAfterNativeContent;
	private String documentName;
	private String version="";
	private String docStructure="1";
	
	public String execute()
	{
		this.setUser((User)session.get(DMSConstants.USER_LOGGED_IN));
		if(this.getUser()==null)
			return LOGIN;
		
		ObjectContainer db=ConnectionPool.getConnection();
		DocFragmentDao dao=new DocFragmentDao(db);
		
		DocFragment df=dao.getLatestDocFragmentVersion(this.getUser().getUsername(), documentName);
		this.version=((Float)(Float.parseFloat(df.getVersionInfo().getVersion())+1.0f)).toString();
		
		this.docFragsBeforeNativeContent=new ArrayList<String>();
		for(DocFragment d:df.getFragsBeforeNativeContent())
		{
			this.docFragsBeforeNativeContent.add(d.getDocId());
		}
		
		this.docFragsAfterNativeContent=new ArrayList<String>();
		for(DocFragment d:df.getFragsAfterNativeContent())
		{
			this.docFragsAfterNativeContent.add(d.getDocId());
		}
		
		this.docFrags1=new ArrayList<String>();
		for(DocFragment d:dao.getReusableDocFragments(this.getUser().getUsername(),documentName))
		{
			if(this.docFragsBeforeNativeContent.indexOf(d.getDocId())==-1
					&& this.docFragsAfterNativeContent.indexOf(d.getDocId())==-1)
				this.docFrags1.add(d.getDocId());
		}
		
		this.docFrags2=new ArrayList<String>();
		for(DocFragment d:dao.getReusableDocFragments(this.getUser().getUsername(),documentName))
		{
			if(this.docFragsBeforeNativeContent.indexOf(d.getDocId())==-1
					&& this.docFragsAfterNativeContent.indexOf(d.getDocId())==-1)
				this.docFrags2.add(d.getDocId());
		}
		
		if(this.docFragsBeforeNativeContent.size()>0)
			this.docStructure="2";
		
		ConnectionPool.freeConnection(db);
		return SUCCESS;
	}
	
	public void setSession(Map<String, Object> arg0)
	{
		this.session=arg0;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<String> getDocFrags1() {
		return docFrags1;
	}

	public void setDocFrags1(List<String> docFrags1) {
		this.docFrags1 = docFrags1;
	}

	public List<String> getDocFragsBeforeNativeContent() {
		return docFragsBeforeNativeContent;
	}

	public void setDocFragsBeforeNativeContent(
			List<String> docFragsBeforeNativeContent) {
		this.docFragsBeforeNativeContent = docFragsBeforeNativeContent;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public List<String> getDocFragsAfterNativeContent() {
		return docFragsAfterNativeContent;
	}

	public void setDocFragsAfterNativeContent(
			List<String> docFragsAfterNativeContent) {
		this.docFragsAfterNativeContent = docFragsAfterNativeContent;
	}

	public List<String> getDocFrags2() {
		return docFrags2;
	}

	public void setDocFrags2(List<String> docFrags2) {
		this.docFrags2 = docFrags2;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDocStructure() {
		return docStructure;
	}

	public void setDocStructure(String docStructure) {
		this.docStructure = docStructure;
	}

}
