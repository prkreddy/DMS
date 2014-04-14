package com.iiitb.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.iiitb.model.DocumentType;

public class DocFragmentInfo
{
	public static enum Access {___, r__, ra_, rae}
	public static enum FileFormat {PDF}
	
	private String name;
	private String description;
	private DocumentType documentType;
	private WorkflowInstance workflowInstance;
	private FileFormat fileFormat;
	private boolean isStandAlone;
	private boolean isReusable;
	private Map<String, Access> accessors;
	private Map<String, DocFragment> allVersions;
	private List<String> keywords;
	
	public DocFragmentInfo(String name, String description, DocumentType documentType, FileFormat fileFormat,
			boolean isStandAlone, boolean isReusable) throws Exception
	{
		this.setName(name);
		this.setDescription(description);
		this.setDocumentType(documentType);
		this.setFileFormat(fileFormat);
		this.setFlags(isStandAlone, isReusable);
		this.setAccessors(new HashMap<String, DocFragmentInfo.Access>());
		this.setAllVersions(new LinkedHashMap<String, DocFragment>());
		this.setKeywords(new ArrayList<String>());
	}
	
	//getters & setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public DocumentType getDocumentType() {
		return documentType;
	}
	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}
	public FileFormat getFileFormat() {
		return fileFormat;
	}
	public void setFileFormat(FileFormat fileFormat) {
		this.fileFormat = fileFormat;
	}
	public boolean isStandAlone() {
		return isStandAlone;
	}
	public boolean isReusable() {
		return isReusable;
	}
	public void setFlags(boolean isStandAlone, boolean isReusable) throws Exception {
		if(!isStandAlone && !isReusable)
			throw new Exception("Both flags cannot be false simultaneously.");
		this.isStandAlone = isStandAlone;
		this.isReusable = isReusable;
	}
	public Map<String, Access> getAccessors() {
		return accessors;
	}
	public void setAccessors(Map<String, Access> accessors) {
		this.accessors = accessors;
	}
	public Map<String, DocFragment> getAllVersions() {
		return allVersions;
	}
	public void setAllVersions(Map<String, DocFragment> allVersions) {
		this.allVersions = allVersions;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public WorkflowInstance getWorkflowInstance() {
		return workflowInstance;
	}

	public void setWorkflowInstance(WorkflowInstance workflowInstance) {
		this.workflowInstance = workflowInstance;
	}

}
