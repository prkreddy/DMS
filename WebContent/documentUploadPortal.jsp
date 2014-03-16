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
	
	<s:form action="documentUploadAction" method="POST" enctype="multipart/form-data">
		<s:textfield key="documentName" name="documentName"
			label="Document name" />
			
		<s:textarea key="description" name="description"
			label="Document description" cols="69" />
			
		<s:label label="Choose constituent documents that go before the native content file"></s:label>
		<s:select id="fragList1" list="docFrags"></s:select>
		<s:submit value="Add" onclick="addFrag1(); return false;"></s:submit>
		<s:select id="fragsBeforeNativeContent" list="{}" multiple="multiple" size="5"></s:select>
		
		<s:file name="uploadFile" label="Choose native content file" size="40" />
		
		<s:label label="Choose constituent documents that go after the native content file"></s:label>
		<s:select id="fragList2" list="docFrags"></s:select>
		<s:submit value="Add" onclick="addFrag2(); return false;"></s:submit>
		<s:select id="fragsAfterNativeContent" list="{}" multiple="multiple" size="5"></s:select>
		
		<s:label label="This document is"></s:label>
		<s:checkbox id="isStandAlone" name="isStandAlone" label="standalone" value="1" onchange="validateFlags1()"></s:checkbox>
		<s:checkbox id="isReusable" name="isReusable" label="reusable" value="1" onchange="validateFlags2()"></s:checkbox>
		<s:submit value="Create" name="submit" />
	</s:form>
	<script type="text/javascript">
		function validateFlags1()
		{
			if(!document.getElementById("isStandAlone").checked &&
					!document.getElementById("isReusable").checked)
			{
				document.getElementById("isStandAlone").checked=true;
				alert("Can't uncheck both at a time.");
			}
		}
		function validateFlags2()
		{
			if(!document.getElementById("isStandAlone").checked &&
					!document.getElementById("isReusable").checked)
			{
				document.getElementById("isReusable").checked=true;
				alert("Can't uncheck both at a time.");
			}
		}
		function addFrag1()
		{
			document.getElementById("fragsBeforeNativeContent").innerHTML+="<option>"+document.getElementById("fragList1").value+"</option>"
		}
		function addFrag2()
		{
			document.getElementById("fragsAfterNativeContent").innerHTML+="<option>"+document.getElementById("fragList2").value+"</option>"
		}
	</script>
</body>
</html>