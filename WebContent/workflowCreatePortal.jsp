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

	<form action="workflowCreateAction" method="post">
		<div style="float: left;">
			Name: <input id="wfname" name="wfname" type="text" /><br /><br />
			Type: <input onchange="usHandler()" type="radio" id="type" name="type" value="us">User-specific <input onchange="rbHandler()" type="radio" id="type" name="type" value="rb">Role-based<br /><br />
			<div id="d1" style="visibility: hidden;">
				<h4>New Activity</h4>
				Name: <input type="text" id="actName" /><br /><br />
				Actor: <s:select id="actActor" theme="simple" list="users"></s:select><br /><br />
				<input type="button" value="Add to workflow" onclick="addActToWf()" />
			</div>
			<div id="d2" style="visibility: hidden; position: absolute;">
				d2
			</div>
		</div>
		<div style="margin-left: 600px;">
			<s:updownselect id="activities" name="activities" multiple="multiple" size="14" list="{}">
			</s:updownselect>
		</div>
	</form>

	<script type="text/javascript">
	
		var d1=document.getElementById("d1");
		var d2=document.getElementById("d2");
		
		function usHandler()
		{
			d2.style.visibility="hidden";
			d2.style.height="0px";
			d1.style.height="400px";
			d1.style.visibility="visible";
		}
		
		function rbHandler()
		{
			d1.style.visibility="hidden";
			d1.style.height="0px";
			d2.style.height="400px";
			d2.style.visibility="visible";
		}
		
		function addActToWf()
		{
			document.getElementById("activities").innerHTML+=
				"<option>"
				+document.getElementById("actName").value
				+"</options>";
		}
		
	</script>
	
</body>
</html>