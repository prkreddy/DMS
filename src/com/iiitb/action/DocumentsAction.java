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

public class DocumentsAction extends ActionSupport implements SessionAware, ServletRequestAware
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8071911410004544472L;
	private Map<String, Object> session;
	private User user;

	private String orderby;

	public String getOrderby()
	{
		return orderby;
	}

	public void setOrderby(String orderby)
	{
		this.orderby = orderby;
	}

	private List<DocFragmentDisplayDetails> docFragmentDisplayDetailsList;

	private Comparator<DocFragmentDisplayDetails> compareByModifiedDate = new Comparator<DocFragmentDisplayDetails>()
	{

		@Override
		public int compare(DocFragmentDisplayDetails o1, DocFragmentDisplayDetails o2)
		{

			return o2.getDateModified().compareTo(o1.getDateModified());
		}

	};

	private Comparator<DocFragmentDisplayDetails> compareByName = new Comparator<DocFragmentDisplayDetails>()
	{

		@Override
		public int compare(DocFragmentDisplayDetails o1, DocFragmentDisplayDetails o2)
		{

			return o1.name.compareTo(o2.name);
		}

	};

	private Comparator<DocFragmentDisplayDetails> compareByCreatedDate = new Comparator<DocFragmentDisplayDetails>()
	{

		@Override
		public int compare(DocFragmentDisplayDetails o1, DocFragmentDisplayDetails o2)
		{

			return o2.dateCreated.compareTo(o1.dateCreated);
		}

	};

	public String execute()
	{
		this.setUser((User) session.get(DMSConstants.USER_LOGGED_IN));
		if (this.getUser() == null)
			return LOGIN;
		ObjectContainer db = ConnectionPool.getConnection();
		DocFragmentDao docFragmentDao = new DocFragmentDao(db); // a(docFragmentDao);
		this.docFragmentDisplayDetailsList = new ArrayList<DocFragmentDisplayDetails>();
		String destpath = servletRequest.getSession().getServletContext().getRealPath("/");
		for (DocFragment df : docFragmentDao.getStandaloneDocFragments(this.getUser().getUsername()))
		{
			if (df.getInfo().isStandAlone())
			{
				DocFragmentDisplayDetails dfd = new DocFragmentDisplayDetails();
				try
				{
					if (df.getBlob() != null && destpath != null && !destpath.trim().equals(""))
					{
						df.getBlob().writeTo(new File(destpath, df.getBlob().getFileName()));
						dfd.pathName = destpath + df.getBlob().getFileName();
					}
				}
				catch (Exception e)
				{
					// e.printStackTrace();
				}

				dfd.docId = df.getDocId();
				dfd.name = df.getInfo().getName();
				dfd.version = df.getVersionInfo().getVersion();
				// dfd.access =
				// df.getInfo().getAccessors().get(this.getUser().getUsername()).toString();
				for (DocFragment d : df.getInfo().getAllVersions().values())
				{
					dfd.dateCreated = d.getVersionInfo().getTimeStamp().toString();
					break;
				}
				dfd.dateModified = df.getVersionInfo().getTimeStamp().toString();
				dfd.actor = df.getVersionInfo().getActor().getUsername();

				if (!user.getRole().getName().equals("admin"))
				{
					Workflow wf = df.getInfo().getWorkflowInstance().getWorkflow();
					String currentActivityName = df.getInfo().getWorkflowInstance().getCurrentActivityName();
					
					if (wf instanceof UserSpecificWorkflow)
					{

						User user = ((UserSpecificWorkflow) wf).getActivitySequence().get(currentActivityName);

						if (user.getName().equals(this.getUser().getName()))
						{
							dfd.setEnableActivityUpdate("true");
						}
						else
						{
							dfd.setEnableActivityUpdate("false");
						}

					}
					else if (wf instanceof RoleBasedWorkflow)
					{

						ThreeTuple tuple = ((RoleBasedWorkflow) wf).getActivitySequence().get(currentActivityName);

						if (tuple.getGroup().getName().equals(this.getUser().getGroup().getName()) && tuple.getRole().getName()
								.equals(this.getUser().getRole().getName()))
						{
							if (checkAlreadyActedOnUser(df.getInfo().getWorkflowInstance().getActorsWhoHaveActed(), user.getEmail()))

								dfd.setEnableActivityUpdate("false");
							else
							{
								dfd.setEnableActivityUpdate("true");
							}

						}
						else
						{
							dfd.setEnableActivityUpdate("false");
						}

					}
					
					//disable if last activity
					if (wf instanceof UserSpecificWorkflow)
					{
						UserSpecificWorkflow u=(UserSpecificWorkflow)wf;
						int i=1;
						for(String s:u.getActivitySequence().keySet())
						{
							if(currentActivityName==s)
								break;
							i++;
						}
						if(i>=u.getActivitySequence().size())
							dfd.setEnableActivityUpdate("false");
					}
					else
					{
						RoleBasedWorkflow u=(RoleBasedWorkflow)wf;
						int i=1;
						for(String s:u.getActivitySequence().keySet())
						{
							if(currentActivityName.equals(s))
								break;
							i++;
						}
						
						if(i>=u.getActivitySequence().size())
							dfd.setEnableActivityUpdate("false");
					}
				}

				this.docFragmentDisplayDetailsList.add(dfd);
			}
		}

		if (orderby == null)
		{
			Collections.sort(this.docFragmentDisplayDetailsList, compareByModifiedDate);
		}
		else if ("Name".equalsIgnoreCase(orderby))
		{
			Collections.sort(this.docFragmentDisplayDetailsList, compareByName);
		}
		else if ("Date Created".equalsIgnoreCase(orderby))
		{
			Collections.sort(this.docFragmentDisplayDetailsList, compareByCreatedDate);
		}
		else if ("Date Modified".equalsIgnoreCase(orderby))
		{
			Collections.sort(this.docFragmentDisplayDetailsList, compareByModifiedDate);
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

	private boolean checkAlreadyActedOnUser(List<User> actorsWhoHaveActed, String email)
	{

		if (actorsWhoHaveActed != null)
		{

			for (User user : actorsWhoHaveActed)
			{

				if (user.getEmail().equals(email))
				{
					return true;
				}
			}
		}
		return false;

	}
}
