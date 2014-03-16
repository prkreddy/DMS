package com.iiitb.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.db4o.ObjectContainer;
import com.iiitb.dao.DocFragmentDao;
import com.iiitb.model.DocFragment;
import com.iiitb.model.DocFragmentDisplayDetails;
import com.iiitb.model.DocFragmentInfo;
import com.iiitb.model.DocFragmentInfo.Access;
import com.iiitb.model.DocFragmentInfo.DocumentType;
import com.iiitb.model.DocFragmentInfo.FileFormat;
import com.iiitb.model.DocFragmentVersionInfo;
import com.iiitb.model.DocFragmentVersionInfo.Action;
import com.iiitb.model.User;
import com.iiitb.util.ConnectionPool;
import com.iiitb.util.DMSConstants;
import com.opensymphony.xwork2.ActionSupport;

public class DocumentsAction extends ActionSupport implements SessionAware, ServletRequestAware
{
	private Map<String, Object> session;
	private User user;
	private List<DocFragmentDisplayDetails> docFragmentDisplayDetailsList;

	// if called, just create a docFragment for testing purpose
	// will be deleted
	/*
	void a(DocFragmentDao dao)
	{
		try
		{
			DocFragmentInfo di = new DocFragmentInfo("doc1", "desc1", DocumentType.Type1, FileFormat.PDF, true, true);
			di.getAccessors().put(this.getUser().getUsername(), Access.ra_);
			DocFragmentVersionInfo vi = new DocFragmentVersionInfo("1.0", this.getUser(), Action.Creation,
					"I created doc1.");
			DocFragment df = new DocFragment("/home/dj/f1.txt", di, vi);
			df.getInfo().getAllVersions().put(df.getVersionInfo().getVersion(), df);
			dao.storeDocFragment(df, new File("/home/dj/f1.txt"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}*/

	public String execute()
	{
		this.setUser((User) session.get(DMSConstants.USER_LOGGED_IN));
		if (this.getUser() == null)
			return LOGIN;
		ObjectContainer db = ConnectionPool.getConnection();
		DocFragmentDao docFragmentDao = new DocFragmentDao(db); // a(docFragmentDao);
		this.docFragmentDisplayDetailsList = new ArrayList<DocFragmentDisplayDetails>();
		String destpath = servletRequest.getSession().getServletContext().getRealPath("/");
		for (DocFragment df : docFragmentDao.getDocFragments(this.getUser().getUsername()).values())
		{
			if(df.getInfo().isStandAlone())
			{
				DocFragmentDisplayDetails dfd = new DocFragmentDisplayDetails();
				try
				{
					if(df.getBlob()!=null && destpath!=null && !destpath.trim().equals(""))
					{
						df.getBlob().writeTo(new File(destpath, df.getBlob().getFileName()));
						dfd.pathName = destpath + df.getBlob().getFileName();
					}
				}
				catch (Exception e)
				{
					//e.printStackTrace();
				}
				
				dfd.docId = df.getDocId();
				dfd.name = df.getInfo().getName();
				dfd.version = df.getVersionInfo().getVersion();
				dfd.access = df.getInfo().getAccessors().get(this.getUser().getUsername()).toString();
				for (DocFragment d : df.getInfo().getAllVersions().values())
				{
					dfd.dateCreated = d.getVersionInfo().getTimeStamp().toString();
					break;
				}
				dfd.dateModified = df.getVersionInfo().getTimeStamp().toString();
				dfd.actor = df.getVersionInfo().getActor().getUsername();
				this.docFragmentDisplayDetailsList.add(dfd);
			}
		}

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

	public List<DocFragmentDisplayDetails> getDocFragmentDisplayDetailsList()
	{
		return docFragmentDisplayDetailsList;
	}

	HttpServletRequest servletRequest;

	@Override
	public void setServletRequest(HttpServletRequest arg0)
	{
		servletRequest = arg0;

	}
}
