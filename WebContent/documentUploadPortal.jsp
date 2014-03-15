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
	<s:form action="documentUploadAction" method="POST"
		enctype="multipart/form-data">
		<s:textfield key="documentName" name="documentName"
			label="Document Name" />
		<s:textarea key="description" name="description"
			label="Document Description" />


		<s:file name="uploadFile" label="Choose File" size="40" />

		<s:radio name="isStandAlone" list="{'standalone','not-standalone'}"></s:radio>
		<s:radio name="isReusable" list="{'reusable','not-reusable'}"></s:radio>

		<s:submit value="Upload" name="submit" />
	</s:form>
</body>
</html>