package com.iiitb.model;

public abstract class Workflow
{
	private String name;

	public Workflow(String name)
	{
		this.name=name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
