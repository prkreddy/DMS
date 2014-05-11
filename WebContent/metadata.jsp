<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>Metadata</h2>

<table>
	<tr>
		<td>Document Name:</td>
		<td><s:property value="df.getInfo().getName()" /></td>
	</tr>
	<tr>
		<td>Version:</td>
		<td><s:property value="df.getVersionInfo().getVersion()" /></td>
	</tr>
	<tr>
		<td>Standalone:</td>
		<td><s:property value="df.getInfo().isStandAlone()" /></td>
	</tr>
	<tr>
		<td>Reusable:</td>
		<td><s:property value="df.getInfo().isReusable()" /></td>
	</tr>
	<tr>
		<td>Created on:</td>
		<td><s:property value="df.getVersionInfo().getTimeStamp().toString()" /></td>
	</tr>
	<tr>
		<td>Created by:</td>
		<td><s:property value="df.getVersionInfo().getActor().getUsername()" /></td>
	</tr>
	<tr>
		<td>Keywords:</td>
		<td><s:property value="df.getInfo().getKeywords()" /></td>
	</tr>
	<tr>
		<td>Document Type</td>
		<td><s:property value="df.getInfo().getDocumentType().getName()" /></td>
	</tr>
	<tr>
		<td>Associated Workflow:</td>
		<td><s:property value="df.getInfo().getWorkflowInstance().getWorkflow().getName()" /></td>
	</tr>
	<tr>
		<td>Workflow Stages:</td>
		<td><s:property value="df.getInfo().getWorkflowInstance().getWorkflow().getActivitySequence().keySet()" /></td>
	</tr>
	<tr>
		<td>Current Workflow Stage:</td>
		<td><s:property value="df.getInfo().getWorkflowInstance().getCurrentActivityName()" /></td>
	</tr>
</table>

</body>
</html>