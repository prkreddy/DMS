package com.iiitb.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.ext.Status;
import com.db4o.query.Predicate;
import com.db4o.types.Blob;
import com.iiitb.model.DocFragment;
import com.iiitb.model.DocFragmentInfo;
import com.iiitb.model.DocFragmentVersionInfo;
import com.iiitb.util.ConnectionPool;

class Predicate1 extends Predicate<DocFragment>
{
	String username;

	Predicate1(String username)
	{
		this.username = username;
	}

	public boolean match(DocFragment arg0)
	{
		return arg0.getInfo().getAccessors().get(username) != null;
	}
}

class PredicateEdit extends Predicate<DocFragment>
{
	String username;
	String docId;

	PredicateEdit(String username, String docId)
	{
		this.username = username;
		this.docId = docId;
	}

	public boolean match(DocFragment arg0)
	{
		return arg0.getInfo().getAccessors().get(username) != null && !(arg0.getDocId().equals(docId));
	}
}

class Predicate2 extends Predicate<DocFragment>
{
	String username;

	Predicate2(String username)
	{
		this.username = username;
	}

	public boolean match(DocFragment arg0)
	{
		return arg0.getInfo().isReusable();
	}
}

class Predicate3 extends Predicate<DocFragment>
{
	String username;

	Predicate3(String username)
	{
		this.username = username;
	}

	public boolean match(DocFragment arg0)
	{
		return arg0.getInfo().isStandAlone();
	}
}

class Predicate4 extends Predicate<DocFragmentInfo>
{
	String username;

	Predicate4(String username)
	{
		this.username = username;
	}

	public boolean match(DocFragmentInfo arg0)
	{
		return arg0.getAccessors().get(username) != null;
	}
}

class Predicate5 extends Predicate<DocFragmentInfo>
{
	String username;
	String docName;

	Predicate5(String username, String docName)
	{
		this.username = username;
		this.docName = docName;
	}

	public boolean match(DocFragmentInfo arg0)
	{
		return arg0.getName().equals(docName);
	}
}

class Predicate6 extends Predicate<DocFragmentInfo>
{
	String username;

	Predicate6(String username)
	{
		this.username = username;
	}

	public boolean match(DocFragmentInfo arg0)
	{
		return (arg0.getAccessors().get(username) != null && arg0.isReusable());
	}
}

/*class Predicate7 extends Predicate<DocFragment>
{
	String username;
	String[] keywordArray;

	Predicate7(String username, String[] keywordArray)
	{
		this.username = username;
		this.keywordArray=keywordArray;
	}

	public boolean match(DocFragment arg0)
	{
		if(!arg0.getInfo().isReusable())
			return false;
		
		for(String kw:arg0.getInfo().getKeywords())
			for(String kw1:keywordArray)
				if(kw.trim().equals(kw1.trim()))
					return true;
		return false;
	}
}*/


class DocumentNameAndVersionPredicate extends Predicate<DocFragment>
{
	String documentName;

	String version;

	DocumentNameAndVersionPredicate(String documentName)
	{
		this.documentName = documentName;
		this.version = version;
	}

	public boolean match(DocFragment arg0)
	{
		return arg0.getDocId().equals(documentName);
	}
}

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

	public DocFragmentDao()
	{
	}

	public DocFragmentDao(ObjectContainer db)
	{
		this.db = db;
	}

	public List<DocFragment> getDocFragments(String username)
	{
		Predicate1 p = new Predicate1(username);
		List<DocFragment> l = db.query(p);
		return l;
	}
	
	public List<DocFragment> getDocFragmentsBasedOnKeywords(String username, String[] keywordArray, String documentName)
	{
		/*if(keywordArray.length==0)
			return getReusableDocFragments(username, documentName);
		
		Predicate7 p = new Predicate7(username, keywordArray);
		List<DocFragment> l = db.query(p);
		return l;*/
		List<DocFragment> lf=new ArrayList<DocFragment>();
		List<DocFragment> l=getReusableDocFragments(username, documentName);
		if(keywordArray.length==0)
			return l;
		for(DocFragment arg0:l)
			for(String kw:arg0.getInfo().getKeywords())
				for(String kw1:keywordArray)
					if(kw.trim().equals(kw1.trim()))
						lf.add(arg0);
		return lf;
	}
	
	public List<DocFragment> getDocFragmentsForEdit(String username, String docId)
	{
		PredicateEdit p = new PredicateEdit(username, docId);
		List<DocFragment> l = db.query(p);
		return l;
	}

	public DocFragment getLatestDocFragmentVersion(String username, String documentName)
	{
		DocFragmentInfo di = this.getDocFragmentInfo(username, documentName);
		float max = 0.0f;
		for (DocFragment d : di.getAllVersions().values())
			if ((Float.parseFloat(d.getVersionInfo().getVersion())) > max)
				max = Float.parseFloat(d.getVersionInfo().getVersion());
		DocFragment df = di.getAllVersions().get(((Float) max).toString());
		return df;
	}

	public List<DocFragmentInfo> getDocFragmentInfos(String username)
	{
		Predicate4 p = new Predicate4(username);
		List<DocFragmentInfo> l = db.query(p);
		return l;
	}
	
	public List<DocFragmentInfo> getReusableDocFragmentInfos(String username)
	{
		Predicate6 p = new Predicate6(username);
		List<DocFragmentInfo> l = db.query(p);
		return l;
	}
	
	public boolean isCircularRefFree(DocFragment testDf, DocFragment srcDf)
	{
		if(testDf.getInfo().getName().equals(srcDf.getInfo().getName()))
			return false;
		if(testDf.getFragsBeforeNativeContent()!=null
				&& testDf.getFragsBeforeNativeContent().size()>0)
			for(DocFragment df:testDf.getFragsBeforeNativeContent())
				if(!isCircularRefFree(df, srcDf))
					return false;
		return true;
	}
	
	public List<DocFragment> getReusableDocFragments(String username, String documentName)
	{
		/*Predicate2 p = new Predicate2(username);
		List<DocFragment> l = db.query(p);

		HashMap<String, DocFragment> frags = new HashMap<String, DocFragment>();
		for (DocFragment df : l)
			frags.put(df.getDocId(), df);

		return l;*/
		DocFragment srcDf=null;
		if(documentName!=null) //edit case
			srcDf=getLatestDocFragmentVersion(username, documentName);
		List<DocFragment> dfl=new ArrayList<DocFragment>();
		for(DocFragmentInfo dfi:getReusableDocFragmentInfos(username))
		{
			DocFragment d=getLatestDocFragmentVersion(username, dfi.getName());
			if(srcDf!=null && d!=null && isCircularRefFree(d, srcDf))
				dfl.add(d); //edit case
			else if(srcDf==null)
				dfl.add(d); //create case
		}
		return dfl;
	}

	public List<DocFragment> getStandaloneDocFragments(String username)
	{
		Predicate3 p = new Predicate3(username);
		List<DocFragment> l = db.query(p);
		return l;
	}

	public DocFragmentInfo getDocFragmentInfo(String username, String docName)
	{
		Predicate5 p = new Predicate5(username, docName);
		List<DocFragmentInfo> l = db.query(p);
		if (l == null || l.size() == 0)
			return null;
		return l.get(0);
	}

	public DocFragment getFragment(String documentName)
	{

		DocumentNameAndVersionPredicate p = new DocumentNameAndVersionPredicate(documentName);

		List<DocFragment> l = db.query(p);

		DocFragment fragment = null;
		if (l != null && l.size() > 0)
		{
			fragment = l.get(0);
		}
		return fragment;

	}

	public boolean storeDocFragment(DocFragment df, File file)
	{
		this.db.store(df);
		if (file == null)
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
			e.printStackTrace();
		}
		return (status == Status.COMPLETED);
	}
}
