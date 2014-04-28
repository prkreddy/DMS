package com.iiitb.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.db4o.ObjectContainer;
import com.db4o.ext.Status;
import com.db4o.types.Blob;
import com.iiitb.dao.DocFragmentDao;
import com.iiitb.model.DocFragment;
import com.iiitb.model.DocFragmentInfo;
import com.iiitb.model.DocFragmentInfo.Access;
import com.iiitb.model.DocumentType;
import com.iiitb.model.DocFragmentInfo.FileFormat;
import com.iiitb.model.DocFragmentVersionInfo;
import com.iiitb.model.DocFragmentVersionInfo.Action;
import com.iiitb.model.RoleBasedWorkflow;
import com.iiitb.model.User;
import com.iiitb.model.UserSpecificWorkflow;
import com.iiitb.model.Workflow;
import com.iiitb.model.WorkflowInstance;
import com.iiitb.util.ConnectionPool;
import com.iiitb.util.DMSConstants;
import com.opensymphony.xwork2.ActionSupport;

public class DocumentUploadAction extends ActionSupport implements SessionAware, ServletRequestAware
{
	private String documentTypeList;
	
	private String workflowList;
	
	private String keywords;
	
	private HttpServletRequest servletRequest;

	private static final long serialVersionUID = 1L;

	private String documentName = "";

	private List<String> fragsBeforeNativeContent;

	private List<String> fragsAfterNativeContent = new ArrayList<String>();

	private String version = "1.0";

	public List<String> getFragsBeforeNativeContent()
	{
		return fragsBeforeNativeContent;
	}

	public void setFragsBeforeNativeContent(List<String> fragsBeforeNativeContent)
	{
		this.fragsBeforeNativeContent = fragsBeforeNativeContent;
	}

	public List<String> getFragsAfterNativeContent()
	{
		return fragsAfterNativeContent;
	}

	public void setFragsAfterNativeContent(List<String> fragsAfterNativeContent)
	{
		this.fragsAfterNativeContent = fragsAfterNativeContent;
	}

	public String getDocumentName()
	{
		return documentName;
	}

	public void setDocumentName(String documentName)
	{
		this.documentName = documentName;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getIsStandAlone()
	{
		return isStandAlone;
	}

	public void setIsStandAlone(String isStandAlone)
	{
		this.isStandAlone = isStandAlone;
	}

	public String getIsReusable()
	{
		return isReusable;
	}

	public void setIsReusable(String isReusable)
	{
		this.isReusable = isReusable;
	}

	public File getUploadFile()
	{
		return uploadFile;
	}

	public void setUploadFile(File uploadFile)
	{
		this.uploadFile = uploadFile;
	}

	public String getUploadFileContentType()
	{
		return uploadFileContentType;
	}

	public void setUploadFileContentType(String uploadFileContentType)
	{
		this.uploadFileContentType = uploadFileContentType;
	}

	public String getUploadFileFileName()
	{
		return uploadFileFileName;
	}

	public void setUploadFileFileName(String uploadFileFileName)
	{
		this.uploadFileFileName = uploadFileFileName;
	}

	private String description;

	private String isStandAlone = "false";

	private String isReusable = "false";

	Map<String, Object> session;

	private File uploadFile;
	private String uploadFileContentType;
	private String uploadFileFileName;

	public Map<String, Object> getSession()
	{
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session)
	{
		this.session = session;
	}

	@Override
	public String execute() throws Exception
	{

		boolean isStandalone = false;
		boolean is_Reusable = false;
		DocFragmentDao dao = new DocFragmentDao();
		ObjectContainer container = ConnectionPool.getConnection();
		dao.setDb(container);

		if (isStandAlone.equals("true"))
		{
			isStandalone = true;
		}
		else
		{
			isStandalone = false;
		}

		if (isReusable.equals("true"))
		{
			is_Reusable = true;
		}
		else
		{
			is_Reusable = false;
		}

		User user = (User) this.session.get(DMSConstants.USER_LOGGED_IN);
		user=dao.getUserByUsername(user.getUsername());
		DocFragmentInfo di;
		DocFragmentVersionInfo vi;
		DocFragment df;
		if ((di = dao.getDocFragmentInfo(user.getUsername(), documentName)) == null)
		{
			di = new DocFragmentInfo(documentName, description, null, FileFormat.PDF, isStandalone, is_Reusable);

			//di.getAccessors().put(user.getUsername(), Access.rae);
			vi = new DocFragmentVersionInfo(this.version, user, Action.Creation, "first commit of " + documentName);
			df = new DocFragment(di, vi);
		}
		else
		// need help on it.
		{
			vi = new DocFragmentVersionInfo(this.version, user, Action.Modification, "modification of " + documentName);
			df = new DocFragment(di, vi);
			// df.setBlob(dao.getLatestDocFragmentVersion(user.getUsername(),
			// documentName).getBlob());
		}

		DocFragment frag = null;
		for (String fragname : fragsBeforeNativeContent)
		{
			frag = dao.getFragment(fragname);
			if (frag != null)
			{
				df.getFragsBeforeNativeContent().add(frag);
			}

		}

		for (String fragname : fragsAfterNativeContent)
		{
			frag = dao.getFragment(fragname);
			if (frag != null)
			{
				df.getFragsAfterNativeContent().add(frag);
			}

		}

		df.getInfo().getAllVersions().put(df.getVersionInfo().getVersion(), df);
		for(String kw:keywords.split("[,]"))
			df.getInfo().getKeywords().add(kw.trim());
		
		df.getInfo().setDocumentType(dao.getDocumentTypeByName(documentTypeList));
		Workflow wf=dao.getWorkflowByName(workflowList);
		String can="";
		if (wf instanceof UserSpecificWorkflow)
		{
			UserSpecificWorkflow uswf=(UserSpecificWorkflow)wf;
			can=uswf.getActivitySequence().keySet().toArray()[0].toString();
		}
		else
		{
			RoleBasedWorkflow rbwf=(RoleBasedWorkflow)wf;
			can=rbwf.getActivitySequence().keySet().toArray()[0].toString();
		}
		if(df.getVersionInfo().getVersion().equals("1.0"))
			df.getInfo().setWorkflowInstance(new WorkflowInstance(wf, can));
		else
		{
			///////////////
		}
				
		// container.store(df.getInfo().getAllVersions());

		File destFile = null;
		if (uploadFileFileName != null)
		{
			String destpath = servletRequest.getSession().getServletContext().getRealPath("/");
			System.out.println("Server path:" + destpath);
			destFile = new File(destpath, uploadFileFileName);
			try
			{
				FileUtils.copyFile(uploadFile, destFile);
			}
			catch (IOException e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}

		dao.storeDocFragment(df, destFile);
		container.close();
		return SUCCESS;
	}

	public String executeEditDocuments() throws Exception
	{

		DocFragmentDao dao = new DocFragmentDao();
		ObjectContainer container = ConnectionPool.getConnection();
		dao.setDb(container);

		User user = (User) this.session.get(DMSConstants.USER_LOGGED_IN);
		DocFragmentInfo di = null;
		DocFragmentVersionInfo vi = null;
		DocFragment df = null;
		di = dao.getDocFragmentInfo(user.getUsername(), documentName);
		if (di != null)
		{
			vi = new DocFragmentVersionInfo("-"+this.version, user, Action.Modification, "modification of " + documentName);
			df = new DocFragment(di, vi);

			DocFragment frag = null;
			for (String fragname : fragsBeforeNativeContent)
			{
				frag = dao.getFragment(fragname);
				if (frag != null)
				{
					df.getFragsBeforeNativeContent().add(frag);
				}

			}

			df.getInfo().getAllVersions().put(df.getVersionInfo().getVersion(), df);
			
			
			String can="";
			WorkflowInstance wfi=(dao.getLatestDocFragmentVersion(user.getUsername(), df.getInfo().getName())).getInfo().getWorkflowInstance();
			Workflow w=wfi.getWorkflow();
			if(w instanceof UserSpecificWorkflow)
			{
				UserSpecificWorkflow uswf=(UserSpecificWorkflow)w;
				can=uswf.getActivitySequence().keySet().toArray()[0].toString();
			}
			else
			{
				RoleBasedWorkflow rbwf=(RoleBasedWorkflow)w;
				can=rbwf.getActivitySequence().keySet().toArray()[0].toString();
			}
			
			wfi.setCurrentActivityName(can);
			df.getInfo().setWorkflowInstance(wfi);
			
			
			
			File destFile = null;
			if (uploadFileFileName != null)
			{
				String destpath = servletRequest.getSession().getServletContext().getRealPath("/");
				System.out.println("Server path:" + destpath);
				destFile = new File(destpath, uploadFileFileName);
				try
				{
					FileUtils.copyFile(uploadFile, destFile);
				}
				catch (IOException e)
				{
					e.printStackTrace();
					return ERROR;
				}
			}

			dao.storeDocFragment(df, destFile);

		}

		//String previousVersion = df.getInfo().getName() + ", v" + ((Float) (Float.parseFloat(df.getVersionInfo().getVersion()) - 1.0f)).toString();

		//List<DocFragment> docFrags = dao.getDocFragmentsForEdit(user.getUsername(), previousVersion);

		//updateDocFrags(container, docFrags, null, previousVersion, df);

		container.close();

		return SUCCESS;
	}

	

	@Override
	public void setServletRequest(HttpServletRequest arg0)
	{
		this.servletRequest = arg0;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDocumentTypeList() {
		return documentTypeList;
	}

	public void setDocumentTypeList(String documentTypeList) {
		this.documentTypeList = documentTypeList;
	}

	public String getWorkflowList() {
		return workflowList;
	}

	public void setWorkflowList(String workflowList) {
		this.workflowList = workflowList;
	}

}
