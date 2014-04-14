package com.iiitb.model;

import java.util.Map;

public class UserSpecificWorkflow extends Workflow
{
	private Map<String, User> activitySequence; //activityName, who

	public UserSpecificWorkflow(String name, Map<String, User> activitySequence)
	{
		super(name);
		this.activitySequence=activitySequence;
	}
	
	public Map<String, User> getActivitySequence() {
		return activitySequence;
	}

	public void setActivitySequence(Map<String, User> activitySequence) {
		this.activitySequence = activitySequence;
	}
}
