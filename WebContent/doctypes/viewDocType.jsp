<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>


	<br />
	<div align="center">
		<p>#descending Order of ModifiedDate</p>
	</div>

	<table border="1" align="center" cellpadding="10">
		<thead>
			<tr>
				<td><b>S.No</b></td>
				<td><b>docTypeName</b></td>
				<td><b>view WorkFlows</b></td>				
			</tr>
		</thead>
		<s:iterator value="docTypes"
			status="stat">
			<tr>

				<td><s:property value="#stat.index" /></td>
				
				<td><s:property/></td>
				<td><button id='<s:property />'
						value='<s:property/>'
						onclick="documentPreview(this)">view</button></td>
			</tr>
		</s:iterator>
	</table>


	<script src="js/jquery-1.10.2.js"></script>

	<script>
		function documentPreview(data) {

			var docId = data.getAttribute("id");
			window.open("viewWorkflows?docTypeName=" + docId,'popUpWindow','height=500,width=400,left=100,top=100,resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no, status=yes') ;
		}

	

	</script>


</body>
</html>