package com.iiitb.dao;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import com.iiitb.model.RoleBasedWorkflow;
import com.iiitb.model.UserSpecificWorkflow;

class PredicateRoleBaseWF extends Predicate<RoleBasedWorkflow>
{
	public boolean match(RoleBasedWorkflow arg0)
	{
		return arg0 != null;
	}
}

class PredicateUserSpecificWF extends Predicate<UserSpecificWorkflow>
{
	public boolean match(UserSpecificWorkflow arg0)
	{
		return arg0 != null;
	}
}

public class WorkFlowDao
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

	public WorkFlowDao()
	{
	}

	public WorkFlowDao(ObjectContainer db)
	{
		this.db = db;
	}

	public List<RoleBasedWorkflow> getRoleBaseWorkFlows()
	{
		PredicateRoleBaseWF p = new PredicateRoleBaseWF();
		List<RoleBasedWorkflow> l = db.query(p);
		return l;
	}

	public List<UserSpecificWorkflow> getUserBaseWorkFlows()
	{
		PredicateUserSpecificWF p = new PredicateUserSpecificWF();
		List<UserSpecificWorkflow> l = db.query(p);
		return l;
	}
}
