package com.iiitb.model;

import java.util.Map;

public class RoleBasedWorkflow extends Workflow
{
	private Map<String, ThreeTuple> activitySequence; //activityName, <group, role, count>

	public RoleBasedWorkflow(String name, Map<String, ThreeTuple> activitySequence)
	{
		super(name);
		this.activitySequence=activitySequence;
	}
	
	public Map<String, ThreeTuple> getActivitySequence() {
		return activitySequence;
	}

	public void setActivitySequence(Map<String, ThreeTuple> activitySequence) {
		this.activitySequence = activitySequence;
	}
}