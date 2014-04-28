

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<sj:head />
</head>
<body>

	<!-- 	<a href="documentCreateAction">Create new document</a> |
	<a href="documentSelectForEditAction">Edit existing document</a> -->




	<s:url var="remoteurl1" action="workflowCreateAction" />
	<s:url var="remoteurl2" action="workflowViewAction" />

	<sj:tabbedpanel id="remotetabs" selectedTab="2" show="true"
		hide="'fade'" collapsible="true" sortable="true">
		<sj:tab id="tab1" href="%{remoteurl1}" label="Create new workflow" />
		<sj:tab id="tab2" href="%{remoteurl2}" label="View workflows" />		
	</sj:tabbedpanel>


</body>
</html>