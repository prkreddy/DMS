package com.iiitb.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.opensymphony.xwork2.ActionSupport;

public class DocumentDownloadAction extends ActionSupport
{
	private InputStream fileInputStream;

	private String filePathName;

	public String getFilePathName()
	{
		return filePathName;
	}

	public void setFilePathName(String filePathName)
	{
		this.filePathName = filePathName;
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
		try {
		fileInputStream = new FileInputStream(new File(filePathName));
		} catch(Exception e) {
			return "failure";
		}
		return SUCCESS;
	}

}
