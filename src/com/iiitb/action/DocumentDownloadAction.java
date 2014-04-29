package com.iiitb.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.PDFMergerUtility;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.db4o.ObjectContainer;
import com.iiitb.dao.DocFragmentDao;
import com.iiitb.model.DocFragment;
import com.iiitb.model.RoleBasedWorkflow;
import com.iiitb.model.ThreeTuple;
import com.iiitb.model.User;
import com.iiitb.model.UserSpecificWorkflow;
import com.iiitb.model.Workflow;
import com.iiitb.util.ConnectionPool;
import com.iiitb.util.DMSConstants;
import com.opensymphony.xwork2.ActionSupport;

public class DocumentDownloadAction extends ActionSupport implements ServletRequestAware, SessionAware
{
	private Map<String, Object> session;
	private InputStream fileInputStream;

	private String documentId;

	private String filePath;

	public String getFilePath()
	{
		return filePath;
	}

	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}

	public String getDocumentId()
	{
		return documentId;
	}

	public void setDocumentId(String documentId)
	{
		this.documentId = documentId;
	}

	public void setFileInputStream(InputStream fileInputStream)
	{
		this.fileInputStream = fileInputStream;
	}

	public InputStream getFileInputStream()
	{
		return fileInputStream;
	}

	public String execute()
	{
		String realPath = servletRequest.getSession().getServletContext().getRealPath("/");
		
		

		File filedir = new File(realPath);
		if (filedir!=null && filedir.isDirectory())
		{

			File files[] = filedir.listFiles();

			for (File fl : files)
			{
				if (fl.isFile() && fl.getName().endsWith(".pdf"))
				{
					fl.delete();
				}
			}
		}

		String finalDocumentPath = realPath + "final.pdf";
		DocFragmentDao dao = new DocFragmentDao();
		ObjectContainer container = ConnectionPool.getConnection();
		dao.setDb(container);
		DocFragment fragment = dao.getFragment(documentId);
		List<String> fileNames = new ArrayList<String>();

		fragment.compile(fileNames, realPath);
		ConnectionPool.freeConnection(container);
		System.out.println(fileNames.size());

		PDFMergerUtility mergePdf = new PDFMergerUtility();

		for (int i = 0; i < fileNames.size(); i++)
		{
			mergePdf.addSource(fileNames.get(i));
			System.out.println(fileNames.get(i));
		}
		mergePdf.setDestinationFileName(finalDocumentPath);
		try
		{
			mergePdf.mergeDocuments();
		}
		catch (COSVisitorException | IOException e1)
		{
			e1.printStackTrace();
		}

		PDDocument doc = null;
		try
		{
			URL file = new URL("file://" + finalDocumentPath);
			doc = PDDocument.load(file);

			List allPages = doc.getDocumentCatalog().getAllPages();
			PDFont font = PDType1Font.HELVETICA_BOLD;
			float fontSize = 12.0f;
			for (int i = 0; i < allPages.size(); i++)
			{
				PDPage page = (PDPage) allPages.get(i);
				PDPageContentStream footercontentStream = new PDPageContentStream(doc, page, true, true);
				footercontentStream.beginText();
				footercontentStream.setFont(font, fontSize);
				footercontentStream.moveTextPositionByAmount((PDPage.PAGE_SIZE_A4.getUpperRightX() / 2) - 40,
						(PDPage.PAGE_SIZE_A4.getLowerLeftX() + 40));
				footercontentStream.drawString("Page " + String.valueOf(i + 1) + " of " + allPages.size());
				footercontentStream.endText();
				footercontentStream.close();
			}
			doc.save(finalDocumentPath);
			setFilePath(finalDocumentPath);

		}
		catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (COSVisitorException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if (doc != null)
			{
				try
				{
					doc.close();
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		try
		{
			fileInputStream = new FileInputStream(new File(finalDocumentPath));
		}
		catch (Exception e)
		{
			return "failure";
		}
		return SUCCESS;
	}

	HttpServletRequest servletRequest;
	
	

	@Override
	public void setServletRequest(HttpServletRequest arg0)
	{
		servletRequest = arg0;

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

	public String updateStatus()
	{

		DocFragmentDao dao = new DocFragmentDao();
		ObjectContainer container = ConnectionPool.getConnection();
		dao.setDb(container);
		DocFragment fragment = dao.getFragment(documentId);

		String currenString = fragment.getInfo().getWorkflowInstance().getCurrentActivityName();

		Workflow wf = fragment.getInfo().getWorkflowInstance().getWorkflow();
		boolean found = false;
		if (wf instanceof UserSpecificWorkflow)
		{
			int i = 1;
			User user = dao.getUserByUsername(((User) session.get(DMSConstants.USER_LOGGED_IN)).getUsername());
			for (Entry<String, User> entry : ((UserSpecificWorkflow) wf).getActivitySequence().entrySet())
			{
				if (found)
				{
					fragment.getInfo().getWorkflowInstance().setCurrentActivityName(entry.getKey());
					if (i == ((RoleBasedWorkflow) wf).getActivitySequence().entrySet().size()
							&& !fragment.getVersionInfo().getVersion().equals("1.0"))
					{
						fragment.getInfo().getAllVersions().remove(fragment.getVersionInfo().getVersion());
						fragment.getVersionInfo().setVersion(fragment.getVersionInfo().getVersion().substring(1));
						fragment.getInfo().getAllVersions().put(fragment.getVersionInfo().getVersion(), fragment);
						String previousVersion = fragment.getInfo().getName() + ", v"
								+ ((Float) (Float.parseFloat(fragment.getVersionInfo().getVersion()) - 1.0f)).toString();
						List<DocFragment> docFrags = dao.getDocFragmentsForEdit(user.getUsername(), previousVersion);
						updateDocFrags(container, docFrags, null, previousVersion, fragment);
					}

					found = false;
					break;
				}

				if (entry.getKey().equals(currenString))
				{
					found = true;
				}

			}

		}
		else if (wf instanceof RoleBasedWorkflow)
		{
			int i = 1;
			User user = dao.getUserByUsername(((User) session.get(DMSConstants.USER_LOGGED_IN)).getUsername());
			for (Entry<String, ThreeTuple> entry : ((RoleBasedWorkflow) wf).getActivitySequence().entrySet())
			{

				if (found)
				{
					fragment.getInfo().getWorkflowInstance().setCurrentActivityName(entry.getKey());

					if (i == ((RoleBasedWorkflow) wf).getActivitySequence().entrySet().size()
							&& !fragment.getVersionInfo().getVersion().equals("1.0"))
					{
						fragment.getInfo().getAllVersions().remove(fragment.getVersionInfo().getVersion());
						fragment.getVersionInfo().setVersion(fragment.getVersionInfo().getVersion().substring(1));
						fragment.getInfo().getAllVersions().put(fragment.getVersionInfo().getVersion(), fragment);
						container.store(fragment.getInfo().getAllVersions());
						String previousVersion = fragment.getInfo().getName() + ", v"
								+ ((Float) (Float.parseFloat(fragment.getVersionInfo().getVersion()) - 1.0f)).toString();
						List<DocFragment> docFrags = dao.getDocFragmentsForEdit(user.getUsername(), previousVersion);
						updateDocFrags(container, docFrags, null, previousVersion, fragment);
					}
					found = false;
					break;
				}

				if (entry.getKey().equals(currenString))
				{

					fragment.getInfo().getWorkflowInstance().getActorsWhoHaveActed().add(user);
					container.store(fragment.getInfo().getWorkflowInstance().getActorsWhoHaveActed());
					System.out.println(fragment.getInfo().getWorkflowInstance().getActorsWhoHaveActed().size());
					System.out.println(((RoleBasedWorkflow) fragment.getInfo().getWorkflowInstance().getWorkflow()).getActivitySequence()
							.get(currenString).getActorCount());

					if (fragment.getInfo().getWorkflowInstance().getActorsWhoHaveActed().size() >= ((RoleBasedWorkflow) fragment.getInfo()
							.getWorkflowInstance().getWorkflow()).getActivitySequence().get(currenString).getActorCount())
					{
						fragment.getInfo().getWorkflowInstance().setActorsWhoHaveActed(new ArrayList<User>());
						found = true;
					}
				}
				i++;
			}

		}

		if (!found)
			container.store(fragment);

		ConnectionPool.freeConnection(container);
		return SUCCESS;
	}

	@Override
	public void setSession(Map<String, Object> arg0)
	{
		// TODO Auto-generated method stub
		this.session = arg0;
	}

	
}
