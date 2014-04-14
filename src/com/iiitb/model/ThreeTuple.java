package com.iiitb.model;

public class ThreeTuple
{
	private UserGroup group;
	private UserRole role;
	private int actorCount; //0 means all
	
	
	public ThreeTuple(UserGroup g, UserRole r, int c)
	{
		this.group=g;
		this.role=r;
		this.actorCount=c;
	}
	
	public UserGroup getGroup() {
		return group;
	}
	public void setGroup(UserGroup group) {
		this.group = group;
	}
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}
	public int getActorCount() {
		return actorCount;
	}
	public void setActorCount(int actorCount) {
		this.actorCount = actorCount;
	}
}