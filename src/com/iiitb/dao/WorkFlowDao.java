package com.iiitb.dao;

import java.util.ArrayList;
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

class PredicateRoleBaseWFwithName extends Predicate<RoleBasedWorkflow>
{

	String name;

	public PredicateRoleBaseWFwithName(String wfName)
	{
		this.name = wfName;
	}

	public boolean match(RoleBasedWorkflow arg0)
	{
		return arg0.getName().equals(this.name);
	}
}

class PredicateUserSpecificWFwithName extends Predicate<UserSpecificWorkflow>
{

	String name;

	public PredicateUserSpecificWFwithName(String wfName)
	{
		this.name = wfName;
	}

	public boolean match(UserSpecificWorkflow arg0)
	{
		return arg0.getName().equals(this.name);
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

	public List<String> getRoleBaseWorkFlowsNames()
	{
		PredicateRoleBaseWF p = new PredicateRoleBaseWF();
		List<RoleBasedWorkflow> l = db.query(p);
		List<String> flows = new ArrayList<String>();
		for (RoleBasedWorkflow wf : l)
		{
			flows.add(wf.getName());
		}
		return flows;
	}

	public List<String> getUserBaseWorkFlowsNames()
	{
		PredicateUserSpecificWF p = new PredicateUserSpecificWF();
		List<UserSpecificWorkflow> l = db.query(p);
		List<String> flows = new ArrayList<String>();
		for (UserSpecificWorkflow wf : l)
		{
			flows.add(wf.getName());
		}
		return flows;
	}

	public RoleBasedWorkflow getRoleBaseWorkFlow(String name)
	{
		PredicateRoleBaseWFwithName p = new PredicateRoleBaseWFwithName(name);
		List<RoleBasedWorkflow> l = db.query(p);

		if (l.size() > 0)
		{
			return l.get(0);
		}
		return null;
	}

	public UserSpecificWorkflow getUserBaseWorkFlow(String name)
	{
		PredicateUserSpecificWFwithName p = new PredicateUserSpecificWFwithName(name);
		List<UserSpecificWorkflow> l = db.query(p);

		if (l.size() > 0)
		{
			return l.get(0);
		}
		return null;
	}
	
	public List<String> getWorkFlows()
	{
		PredicateRoleBaseWF p = new PredicateRoleBaseWF();
		List<RoleBasedWorkflow> l = db.query(p);
		List<String> flows = new ArrayList<String>();
		for (RoleBasedWorkflow wf : l)
		{
			flows.add(wf.getName());
		}
		return flows;
	}

	

	
	

}
