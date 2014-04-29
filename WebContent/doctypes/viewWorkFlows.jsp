<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>


	<br />

	<div align="center">
		Workflow's for DocumentType :
		<h2><s:property value="docTypeName" /></h2>
	</div>


	<table border="1" align="center" cellpadding="10">
		<thead>
			<tr>
				<td><b>S.No</b></td>
				<td><b>WorkFlowName</b></td>
			</tr>
		</thead>

		<tr>
			<th colspan="2" align="left" style="color: blue;">roleWorkflows</th>
		</tr>
		<s:iterator value="roleWorkflows" status="stat">
			<tr>

				<td><s:property value="%{#stat.index + 1}" /></td>
				<td><s:property /></td>
			</tr>
		</s:iterator>
		<tr>
			<th colspan="2" align="left" style="color: blue;">userworkflows</th>
		</tr>
		<s:iterator value="userworkflows" status="stat">
			<tr>
				<td><s:property value="%{#stat.index + 1}" /></td>
				<td><s:property /></td>
			</tr>
		</s:iterator>

	</table>



</body>
</html>