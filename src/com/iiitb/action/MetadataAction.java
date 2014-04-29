package com.iiitb.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.db4o.ObjectContainer;
import com.iiitb.dao.DocFragmentDao;
import com.iiitb.model.DocFragment;
import com.iiitb.model.DocFragmentDisplayDetails;
import com.iiitb.model.RoleBasedWorkflow;
import com.iiitb.model.ThreeTuple;
import com.iiitb.model.User;
import com.iiitb.model.UserSpecificWorkflow;
import com.iiitb.model.Workflow;
import com.iiitb.util.ConnectionPool;
import com.iiitb.util.DMSConstants;
import com.opensymphony.xwork2.ActionSupport;

public class MetadataAction extends ActionSupport implements SessionAware
{
	/**
	 * 
	 */
	
	private Map<String, Object> session;
	private User user;
	public String docId;
	public DocFragment df;
	
	public String execute()
	{
		this.setUser((User) session.get(DMSConstants.USER_LOGGED_IN));
		if (this.getUser() == null)
			return LOGIN;
		ObjectContainer db = ConnectionPool.getConnection();
		DocFragmentDao dao = new DocFragmentDao(db); // a(docFragmentDao);
		
		df=dao.getFragment(docId);
		
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
}
