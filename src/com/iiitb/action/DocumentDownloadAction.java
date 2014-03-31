package com.iiitb.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.util.PDFMergerUtility;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.db4o.ObjectContainer;
import com.iiitb.dao.DocFragmentDao;
import com.iiitb.model.DocFragment;
import com.iiitb.util.ConnectionPool;
import com.opensymphony.xwork2.ActionSupport;

public class DocumentDownloadAction extends ActionSupport implements ServletRequestAware
{
	private InputStream fileInputStream;

	private String documentId;

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

}
