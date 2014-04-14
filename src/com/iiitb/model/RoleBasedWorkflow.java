package com.iiitb.model;

import java.util.Map;

public class RoleBasedWorkflow extends Workflow
{
	Map<String, ThreeTuple> activitySequence; //activityName, <group, role, count>
}

class ThreeTuple
{
	UserGroup group;
	UserRole role;
	int actorCount; //0 means all
}