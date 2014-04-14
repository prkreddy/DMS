<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<s:form action="docTypeCreateAction" method="post">

		<table cellspacing="10">
			<tr>
				<td colspan="2"><s:textfield name="docTypeName"
						id="docTypeName" label="DocType Name" /></td>
			</tr>
			<tr></tr>
			<tr></tr>
			<tr>
				<th align="left">roleWorkflows</th>
				<th align="left">userworkflows</th>
			</tr>
			<tr>
				<td><s:select list="roleWorkflows" name="roleWorkflows"
						theme="simple" multiple="true" size="5" /></td>
				<td><s:select list="userworkflows" name="userworkflows"
						theme="simple" multiple="true" size="5" /></td>
			</tr>

			<tr>
				<td><s:submit value="Done" /></td>
			</tr>
		</table>
	</s:form>
</body>
</html>