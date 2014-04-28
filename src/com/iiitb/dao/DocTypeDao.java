package com.iiitb.dao;

import java.util.ArrayList;
import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import com.iiitb.model.DocumentType;
import com.iiitb.model.RoleBasedWorkflow;
import com.iiitb.model.UserSpecificWorkflow;

class PredicateDocumentType extends Predicate<DocumentType>
{
	public boolean match(DocumentType arg0)
	{
		return arg0.getName().equals(this.name);
	}

	public PredicateDocumentType(String docname)
	{
		this.name = docname;
	}

	String name;

}

public class DocTypeDao
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

	public DocTypeDao()
	{
	}

	public DocTypeDao(ObjectContainer db)
	{
		this.db = db;
	}

	public DocumentType getDocType(String docName)
	{
		PredicateDocumentType p = new PredicateDocumentType(docName);
		List<DocumentType> l = db.query(p);
		if (l != null && l.size() > 0)
		{
			return l.get(0);
		}
		else
		{
			return null;
		}

	}

}
