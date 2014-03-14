package com.iiitb.model;

import java.util.Date;
import java.util.Map;

public class DocFragmentInfo
{
	public static enum Access {ReadOnly, Edit, Append, No};
	public static enum LifecycleStage {Created, InUse, Archived};
	public static enum FileFormat {PDF};
	
	private String name;
	private String description;
	private String documentType;
	private FileFormat fileFormat;
	private boolean isStandAlone;
	private boolean isReusable;
	private Map<User, Access> accessors;
	private Map<Date, LifecycleStage> lifecycleStages;
	private Map<String, DocFragment> allVersions;
	
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
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
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
	public Map<User, Access> getAccessors() {
		return accessors;
	}
	public void setAccessors(Map<User, Access> accessors) {
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
