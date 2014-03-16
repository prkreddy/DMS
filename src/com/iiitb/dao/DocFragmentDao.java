package com.iiitb.dao;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.db4o.ObjectContainer;
import com.db4o.ext.Status;
import com.db4o.types.Blob;
import com.iiitb.model.DocFragment;

public class DocFragmentDao
{
	private ObjectContainer db;

	public ObjectContainer getDb()
	{
		return db;
	}

	public void setDb(ObjectContainer db)
	{
		this.db = db;
	}

	private Map<String, DocFragment> hm;

	public DocFragmentDao()
	{

	}

	public DocFragmentDao(ObjectContainer db)
	{
		this.db = db;
		hm = new HashMap<String, DocFragment>();
		List<DocFragment> l = this.db.queryByExample(DocFragment.class);
		if (l != null && l.size() != 0)
			for (DocFragment df : l)
				hm.put(df.getDocId(), df);
	}

	public int getDocFragmentCount()
	{
		return this.hm.size();
	}

	public Map<String, DocFragment> getDocFragments(String username)
	{
		HashMap<String, DocFragment> frags = new HashMap<String, DocFragment>();
		Set<Map.Entry<String, DocFragment>> set = hm.entrySet();
		for (Map.Entry<String, DocFragment> e : set)
			if (e.getValue().getInfo().getAccessors().get(username) != null)
				frags.put(e.getKey(), e.getValue());
		return frags;
	}
	
	public Map<String, DocFragment> getReusableDocFragments(String username)
	{
		HashMap<String, DocFragment> frags = new HashMap<String, DocFragment>();
		Set<Map.Entry<String, DocFragment>> set = hm.entrySet();
		for (Map.Entry<String, DocFragment> e : set)
			if (e.getValue().getInfo().getAccessors().get(username) != null)
				if(e.getValue().getInfo().isReusable())
					frags.put(e.getKey(), e.getValue());
		return frags;
	}
	
	public boolean storeDocFragment(DocFragment df, File file)
	{
		this.db.store(df);
		if(file==null)
			return true;
		double status = -10.0;
		try
		{
			Blob blob = df.getBlob();
			blob.readFrom(file);

			status = blob.getStatus();
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

		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (status == Status.COMPLETED);
	}
}
