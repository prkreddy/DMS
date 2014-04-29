

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

	<s:url var="remoteurl1" action="addRoles" />
	<s:url var="remoteurl2" action="addRolesToUsers" />

	<sj:tabbedpanel id="remotetabs" selectedTab="2" show="true"
		hide="'fade'" collapsible="true" sortable="true">
		<sj:tab id="tab1" href="%{remoteurl1}" label="Create new Role" />
		<sj:tab id="tab2" href="%{remoteurl2}" label="Assign Roles to Users" />		
	</sj:tabbedpanel>


</body>
</html>