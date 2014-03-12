package com.iiitb.model;

import com.db4o.types.Blob;

public class DocFragment
{
	private int accessCount;

	private Blob blob;

	private String fileName = null;

	String title;

	String author;

	String subject;

	String keywords;

	String createdBy;

	String documentType;

	String fileType;

	RevisionRecord revisionRecord;

	public int getAccessCount()
	{
		return accessCount;
	}

	public void setAccessCount(int accessCount)
	{
		this.accessCount = accessCount;
	}

	public Blob getBlob()
	{
		return blob;
	}

	public void setBlob(Blob blob)
	{
		this.blob = blob;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public String getKeywords()
	{
		return keywords;
	}

	public void setKeywords(String keywords)
	{
		this.keywords = keywords;
	}

	public String getCreatedBy()
	{
		return createdBy;
	}

	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

	public String getDocumentType()
	{
		return documentType;
	}

	public void setDocumentType(String documentType)
	{
		this.documentType = documentType;
	}

	public String getFileType()
	{
		return fileType;
	}

	public void setFileType(String fileType)
	{
		this.fileType = fileType;
	}

}
