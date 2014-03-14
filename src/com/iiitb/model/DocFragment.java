package com.iiitb.model;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class DocFragment
{
	private List<DocFragment> fragsBeforeNativeContent;
	private File nativeContentFile;
	private List<DocFragment> fragsAfterNativeContent;
	private DocFragmentVersionInfo versionInfo;
	private DocFragmentInfo info;
	
	public DocFragment(String fName, DocFragmentInfo di, DocFragmentVersionInfo vi)
	{
		this.setFragsBeforeNativeContent(new ArrayList<DocFragment>());
		if(!fName.trim().equals(""))
			this.setNativeContentFile(new File(fName));
		this.setFragsAfterNativeContent(new ArrayList<DocFragment>());
		this.setInfo(di);
		this.setVersionInfo(vi);
	}
	
	void display()
	{
		if(this.getFragsBeforeNativeContent()!=null && this.getFragsBeforeNativeContent().size()>0)
			for(DocFragment df:this.getFragsBeforeNativeContent())
				df.display();
		if(this.getNativeContentFile()!=null && this.getNativeContentFile().exists())
		{
			try
			{
				FileInputStream fis=new FileInputStream(this.getNativeContentFile());
				int ch;
				while((ch=fis.read())!=-1)
					System.out.print((char)ch);
				fis.close();
			}
			catch(Exception e)
			{
				System.out.println("Error when reading from file.");
			}
		}
		if(this.getFragsAfterNativeContent()!=null && this.getFragsAfterNativeContent().size()>0)
			for(DocFragment df:this.getFragsAfterNativeContent())
				df.display();
	}
	
	//getters & setters
	public List<DocFragment> getFragsBeforeNativeContent() {
		return fragsBeforeNativeContent;
	}
	public void setFragsBeforeNativeContent(List<DocFragment> fragsBeforeNativeContent) {
		this.fragsBeforeNativeContent = fragsBeforeNativeContent;
	}
	public File getNativeContentFile() {
		return nativeContentFile;
	}
	public void setNativeContentFile(File nativeContentFile) {
		this.nativeContentFile = nativeContentFile;
	}
	public List<DocFragment> getFragsAfterNativeContent() {
		return fragsAfterNativeContent;
	}
	public void setFragsAfterNativeContent(List<DocFragment> fragsAfterNativeContent) {
		this.fragsAfterNativeContent = fragsAfterNativeContent;
	}
	public DocFragmentVersionInfo getVersionInfo() {
		return versionInfo;
	}
	public void setVersionInfo(DocFragmentVersionInfo versionInfo) {
		this.versionInfo = versionInfo;
	}
	public DocFragmentInfo getInfo() {
		return info;
	}
	public void setInfo(DocFragmentInfo info) {
		this.info = info;
	}
}
