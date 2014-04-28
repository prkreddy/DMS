package com.iiitb.model;

import java.util.ArrayList;
import java.util.List;

public class WorkflowInstance
{
	private Workflow workflow;
	private String currentActivityName;
	private List<User> actorsWhoHaveActed;

	public WorkflowInstance(Workflow wf, String can)
	{
		this.workflow=wf;
		this.currentActivityName=can;
		actorsWhoHaveActed=new ArrayList<User>();
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	public String getCurrentActivityName() {
		return currentActivityName;
	}

	public void setCurrentActivityName(String currentActivityName) {
		this.currentActivityName = currentActivityName;
	}

	public List<User> getActorsWhoHaveActed() {
		return actorsWhoHaveActed;
	}

	public void setActorsWhoHaveActed(List<User> actorsWhoHaveActed) {
		this.actorsWhoHaveActed = actorsWhoHaveActed;
	}
}
