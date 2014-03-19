package com.iiitb.action;

import java.util.ArrayList;
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

public class DocumentSelectForEditAction extends ActionSupport implements SessionAware
{
	private User user;
	private Map<String, Object> session;
	private List<String> docFragInfos;
	
	public String execute()
	{
		this.setUser((User)session.get(DMSConstants.USER_LOGGED_IN));
		if(this.getUser()==null)
			return LOGIN;
		
		ObjectContainer db=ConnectionPool.getConnection();
		DocFragmentDao dao=new DocFragmentDao(db);
		
		this.docFragInfos=new ArrayList<String>();
		for(DocFragmentInfo di:dao.getDocFragmentInfos(this.getUser().getUsername()))
			this.docFragInfos.add(di.getName());
		
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

	public List<String> getDocFragInfos() {
		return docFragInfos;
	}

	public void setDocFragInfos(List<String> docFragInfos) {
		this.docFragInfos = docFragInfos;
	}
}
