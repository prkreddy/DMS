package com.iiitb.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DocFragmentVersionInfo
{
	public static enum Action {Creation, Modification, Deletion}
	
	private String version;
	private User actor;
	private Date timeStamp;
	private Action action;
	private String comments;
	
	public DocFragmentVersionInfo(String version, User actor, Action action, String comments)
	{
		this.setVersion(version);
		this.setActor(actor);
		this.setTimeStamp(new Date());
		this.setAction(action);
		this.setComments(comments);
	}
	
	//getters & setters
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public User getActor() {
		return actor;
	}
	public void setActor(User actor) {
		this.actor = actor;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
}
