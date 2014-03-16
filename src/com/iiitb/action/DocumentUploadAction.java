package com.iiitb.action;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.iiitb.dao.DocFragmentDao;
import com.iiitb.model.DocFragment;
import com.iiitb.model.DocFragmentInfo;
import com.iiitb.model.DocFragmentInfo.Access;
import com.iiitb.model.DocFragmentInfo.DocumentType;
import com.iiitb.model.DocFragmentInfo.FileFormat;
import com.iiitb.model.DocFragmentVersionInfo;
import com.iiitb.model.DocFragmentVersionInfo.Action;
import com.iiitb.model.User;
import com.iiitb.util.DMSConstants;
import com.opensymphony.xwork2.ActionSupport;

public class DocumentUploadAction extends ActionSupport implements SessionAware, ServletRequestAware
{	
	private HttpServletRequest servletRequest;
	
	private static final long serialVersionUID = 1L;

	private String documentName;

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

	private String isStandAlone;

	private String isReusable;

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
		DocFragmentInfo di = new DocFragmentInfo(documentName, description, DocumentType.Type1, FileFormat.PDF,
				isStandalone, is_Reusable);
		User user = (User) this.session.get(DMSConstants.USER_LOGGED_IN);
		di.getAccessors().put(user.getUsername(), Access.rae);
		DocFragmentVersionInfo vi = new DocFragmentVersionInfo("1.0", user, Action.Creation, "I created doc1.");
		DocFragment df = new DocFragment(di, vi);
		df.getInfo().getAllVersions().put(df.getVersionInfo().getVersion(), df);
		DocFragmentDao dao = new DocFragmentDao();
		File destFile=null;
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
			// user.setPhoto(inputStream);
		}
		ObjectContainer container = Db4oEmbedded.openFile(DMSConstants.db4oPath + DMSConstants.db4oFileName);
		dao.setDb(container);
		dao.storeDocFragment(df, destFile);
		container.close();
		return SUCCESS;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0)
	{
		this.servletRequest = arg0;
	}

}
