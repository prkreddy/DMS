package com.iiitb.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DocFragmentInfo
{
	public static enum Access {___, r__, ra_, rae}
	public static enum LifecycleStage {Created, InUse, Archived}
	public static enum DocumentType {Type1, Type2}
	public static enum FileFormat {PDF}
	
	private String name;
	private String description;
	private DocumentType documentType;
	private FileFormat fileFormat;
	private boolean isStandAlone;
	private boolean isReusable;
	private Map<String, Access> accessors;
	private Map<Date, LifecycleStage> lifecycleStages;
	private Map<String, DocFragment> allVersions;
	
	public DocFragmentInfo(String name, String description, DocumentType documentType, FileFormat fileFormat,
			boolean isStandAlone, boolean isReusable) throws Exception
	{
		this.setName(name);
		this.setDescription(description);
		this.setDocumentType(documentType);
		this.setFileFormat(fileFormat);
		this.setFlags(isStandAlone, isReusable);
		this.setAccessors(new HashMap<String, DocFragmentInfo.Access>());
		this.setLifecycleStages(new HashMap<Date, DocFragmentInfo.LifecycleStage>());
		this.setAllVersions(new HashMap<String, DocFragment>());
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
	public Map<Date, LifecycleStage> getLifecycleStages() {
		return lifecycleStages;
	}
	public void setLifecycleStages(Map<Date, LifecycleStage> lifecycleStages) {
		this.lifecycleStages = lifecycleStages;
	}
	public Map<String, DocFragment> getAllVersions() {
		return allVersions;
	}
	public void setAllVersions(Map<String, DocFragment> allVersions) {
		this.allVersions = allVersions;
	}
}
