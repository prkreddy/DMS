package com.iiitb.model;

public class WorkflowInstance
{
	private Workflow workflow;
	private String currentActivityName;

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}
}
