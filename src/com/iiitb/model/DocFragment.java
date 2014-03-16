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

	void compile()
	{
		if (this.getFragsBeforeNativeContent() != null && this.getFragsBeforeNativeContent().size() > 0)
			for (DocFragment df : this.getFragsBeforeNativeContent())
				df.compile();
		if (this.blob != null)
		{
			try
			{
				blob.writeTo(new File(DMSConstants.db4oPath + "sample.txt"));
				double status = blob.getStatus();
				while (status > Status.COMPLETED)
				{
					try
					{
						Thread.sleep(50);
						status = blob.getStatus();
					}
					catch (InterruptedException ex)
					{
						System.out.println(ex.getMessage());
					}
				}
				FileInputStream fis = new FileInputStream(new File(DMSConstants.db4oPath + "sample.txt"));
				int ch;
				while ((ch = fis.read()) != -1)
					System.out.print((char) ch);
				fis.close();
			}
			catch (Exception e)
			{
				System.out.println("Error when reading from file.");
			}
		}
		if (this.getFragsAfterNativeContent() != null && this.getFragsAfterNativeContent().size() > 0)
			for (DocFragment df : this.getFragsAfterNativeContent())
				df.compile();
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
		return docId;
	}
}
