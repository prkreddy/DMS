package com.iiitb.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.db4o.ObjectContainer;
import com.iiitb.dao.DocFragmentDao;
import com.iiitb.model.DocFragment;
import com.iiitb.model.DocFragmentInfo;
import com.iiitb.model.DocFragmentInfo.DocumentType;
import com.iiitb.model.DocFragmentInfo.FileFormat;
import com.iiitb.model.DocFragmentVersionInfo;
import com.iiitb.model.DocFragmentVersionInfo.Action;
import com.iiitb.model.User;
import com.iiitb.model.DocFragmentInfo.Access;
import com.iiitb.util.ConnectionPool;
import com.iiitb.util.DMSConstants;
import com.opensymphony.xwork2.ActionSupport;

public class DocumentsAction extends ActionSupport implements SessionAware
{
	private Map<String, Object> session;
	private User user;
	private Map<String, DocFragment> docFragments;
	
	//if called, just create a docFragment for testing purpose
	//will be deleted
	void a(DocFragmentDao dao)
	{
		try
		{
			DocFragmentInfo di=new DocFragmentInfo(
					"doc1",
					"desc1",
					DocumentType.Type1,
					FileFormat.PDF,
					true,
					true);
			di.getAccessors().put(this.getUser().getUsername(), Access.Edit);
			DocFragmentVersionInfo vi=new DocFragmentVersionInfo("1.0", this.getUser(), Action.Creation, "I created doc1.");
			DocFragment df=new DocFragment("/home/dj/f1.txt", di, vi);
			dao.storeDocFragment(df);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public String execute()
	{
		this.setUser((User)session.get(DMSConstants.USER_LOGGED_IN));
		if(this.getUser()==null)
			return LOGIN;
		this.docFragments=new HashMap<String, DocFragment>();
		ObjectContainer db=ConnectionPool.getConnection();
		DocFragmentDao docFragmentDao=new DocFragmentDao(db); //a(docFragmentDao);
		docFragments.putAll(docFragmentDao.getDocFragments(this.getUser().getUsername(), Access.ReadOnly));
		docFragments.putAll(docFragmentDao.getDocFragments(this.getUser().getUsername(), Access.Append));
		docFragments.putAll(docFragmentDao.getDocFragments(this.getUser().getUsername(), Access.Edit));
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

	public Map<String, DocFragment> getDocFragments() {
		return docFragments;
	}
}
