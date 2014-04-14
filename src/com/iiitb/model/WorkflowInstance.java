package com.iiitb.model;

public class WorkflowInstance
{
	private Workflow workflow;
	private String currentActivityName;

	public WorkflowInstance(Workflow wf, String can)
	{
		this.workflow=wf;
		this.currentActivityName=can;
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
}
