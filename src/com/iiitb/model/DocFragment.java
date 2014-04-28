package com.iiitb.model;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import com.db4o.ext.Status;
import com.db4o.types.Blob;
import com.iiitb.util.DMSConstants;

public class DocFragment
{
	private String docId;
	private List<DocFragment> fragsBeforeNativeContent;
	private Blob blob;
	private List<DocFragment> fragsAfterNativeContent;
	private DocFragmentVersionInfo versionInfo;
	private DocFragmentInfo info;

	public DocFragment(DocFragmentInfo di, DocFragmentVersionInfo vi)
	{
		this.setFragsBeforeNativeContent(new ArrayList<DocFragment>());

		this.setFragsAfterNativeContent(new ArrayList<DocFragment>());
		this.setInfo(di);
		this.setVersionInfo(vi);
		this.docId = this.getInfo().getName() + ", v" + this.getVersionInfo().getVersion();
	}

	public void compile(List<String> fileNames, String path)
	{

		if (this.getFragsBeforeNativeContent() != null && this.getFragsBeforeNativeContent().size() > 0)
			for (DocFragment df : this.getFragsBeforeNativeContent())
				df.compile(fileNames, path);
		if (this.blob != null && this.blob.getFileName() != null)
		{
			try
			{
				this.blob.writeTo(new File(path + this.blob.getFileName()));
				fileNames.add(path + this.blob.getFileName());
				double status = this.blob.getStatus();
				while (status > Status.COMPLETED)
				{
					try
					{
						Thread.sleep(50);
						status = this.blob.getStatus();
					}
					catch (InterruptedException ex)
					{
						System.out.println(ex.getMessage());
					}
				}

			}
			catch (Exception e)
			{
				System.out.println("Error when reading from file.");
				e.printStackTrace();
			}
		}
		if (this.getFragsAfterNativeContent() != null && this.getFragsAfterNativeContent().size() > 0)
			for (DocFragment df : this.getFragsAfterNativeContent())
				df.compile(fileNames, path);

	}

	// getters & setters
	public List<DocFragment> getFragsBeforeNativeContent()
	{
		return fragsBeforeNativeContent;
	}

	public void setFragsBeforeNativeContent(List<DocFragment> fragsBeforeNativeContent)
	{
		this.fragsBeforeNativeContent = fragsBeforeNativeContent;
	}

	public List<DocFragment> getFragsAfterNativeContent()
	{
		return fragsAfterNativeContent;
	}

	public Blob getBlob()
	{
		return blob;
	}

	public void setBlob(Blob blob)
	{
		this.blob = blob;
	}

	public void setFragsAfterNativeContent(List<DocFragment> fragsAfterNativeContent)
	{
		this.fragsAfterNativeContent = fragsAfterNativeContent;
	}

	public DocFragmentVersionInfo getVersionInfo()
	{
		return versionInfo;
	}

	public void setVersionInfo(DocFragmentVersionInfo versionInfo)
	{
		this.versionInfo = versionInfo;
	}

	public DocFragmentInfo getInfo()
	{
		return info;
	}

	public void setInfo(DocFragmentInfo info)
	{
		this.info = info;
	}

	public String getDocId()
	{
		this.docId = this.getInfo().getName() + ", v" + this.getVersionInfo().getVersion();
		return docId;
	}
}
