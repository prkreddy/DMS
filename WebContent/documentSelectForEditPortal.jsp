<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Success</title>
</head>
<body>

	<s:form action="documentEditAction" method="POST">
		<s:select key="documentName" name="documentName"
			list="docFragInfos" label="Select document for editing"></s:select>
		
		<s:submit value="Edit" name="submit" />
	</s:form>
	
</body>
</html>