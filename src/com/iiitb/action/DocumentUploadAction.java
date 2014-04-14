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
import com.iiitb.model.User;
import com.iiitb.util.ConnectionPool;
import com.iiitb.util.DMSConstants;
import com.opensymphony.xwork2.ActionSupport;

public class DocumentUploadAction extends ActionSupport implements SessionAware, ServletRequestAware
{
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
		DocFragmentInfo di;
		DocFragmentVersionInfo vi;
		DocFragment df;
		if ((di = dao.getDocFragmentInfo(user.getUsername(), documentName)) == null)
		{
			di = new DocFragmentInfo(documentName, description, null, FileFormat.PDF, isStandalone, is_Reusable);

			di.getAccessors().put(user.getUsername(), Access.rae);
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
			vi = new DocFragmentVersionInfo(this.version, user, Action.Modification, "modification of " + documentName);
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

		String previousVersion = df.getInfo().getName() + ", v" + ((Float) (Float.parseFloat(df.getVersionInfo().getVersion()) - 1.0f)).toString();

		List<DocFragment> docFrags = dao.getDocFragmentsForEdit(user.getUsername(), previousVersion);

		updateDocFrags(container, docFrags, null, previousVersion, df);

		container.close();

		return SUCCESS;
	}

	private void updateDocFrags(ObjectContainer container, List<DocFragment> docFrags, DocFragment parentFrag, String docId,
			DocFragment updatedDocFrag)
	{

		int length = docFrags.size();

		for (int i = 0; i < length; i++)
		{
			if (docFrags.get(i).getFragsBeforeNativeContent().size() > 0)
			{
				updateDocFrags(container, docFrags.get(i).getFragsBeforeNativeContent(), docFrags.get(i), docId, updatedDocFrag);
			}
			if (docFrags.get(i).getDocId().equals(docId))
			{
				docFrags.set(i, updatedDocFrag);

				/*
				 * try { Blob blob = docFrags.get(i).getBlob(); File destFile =
				 * null; String destpath =
				 * servletRequest.getSession().getServletContext
				 * ().getRealPath("/"); destFile = new File(destpath,
				 * updatedDocFrag.getBlob().getFileName());
				 * updatedDocFrag.getBlob().writeTo(destFile);
				 * blob.readFrom(destFile);
				 * 
				 * double status = blob.getStatus(); while (status >
				 * Status.COMPLETED) { try { Thread.sleep(50); status =
				 * blob.getStatus(); } catch (InterruptedException ex) {
				 * System.out.println(ex.getMessage()); } }
				 * 
				 * } catch (IOException e) { e.printStackTrace(); }
				 */
			}
			container.store(parentFrag);
			container.commit();
		}

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

}
