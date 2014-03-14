package com.iiitb.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.db4o.ObjectContainer;
import com.iiitb.model.DocFragment;
import com.iiitb.model.DocFragmentInfo;

public class DocFragmentDao
{
	private ObjectContainer db;
	private Map<String, DocFragment> hm;
	
	public DocFragmentDao(ObjectContainer db)
	{
		this.db=db;
		hm=new HashMap<String, DocFragment>();
		List<DocFragment> l=this.db.queryByExample(DocFragment.class);
		if(l!=null && l.size()!=0)
			for(DocFragment df:l)
				hm.put(df.getDocId(), df);
	}
	
	public int getDocFragmentCount()
	{
		return this.hm.size();
	}
	
	public Map<String, DocFragment> getDocFragments(String username, DocFragmentInfo.Access access)
	{
		HashMap<String, DocFragment> frags=new HashMap<String, DocFragment>();
		Set<Map.Entry<String, DocFragment>> set=hm.entrySet();
		for(Map.Entry<String, DocFragment> e:set)
			if(e.getValue().getInfo().getAccessors().get(username)==access)
				frags.put(e.getKey(), e.getValue());
		return frags;
	}
	
	public void storeDocFragment(DocFragment df)
	{
		this.db.store(df);
	}
}
