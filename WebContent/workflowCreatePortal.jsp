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

	<form id='f1' action="workflowCreateAction" method="post">
		<div style="float: left;">
			Name: <input id="wfname" name="wfname" type="text" /><br /><br />
			Type: <input onchange="usHandler()" type="radio" id="wftype" name="wftype" value="us">User-specific <input onchange="rbHandler()" type="radio" id="wftype" name="wftype" value="rb">Role-based<br /><br />
			<div id="d1" style="visibility: hidden;">
				<h4>New Activity</h4>
				Name: <input type="text" id="actName" /><br /><br />
				Actor: <s:select id="actActor" theme="simple" list="users"></s:select><br /><br />
				<input type="button" value="Add to workflow" onclick="addActToWf()" />
			</div>
			<div id="d2" style="visibility: hidden;">
				<h4>New Activity</h4>
				Name: <input type="text" id="actName2" /><br /><br />
				Group: <s:select id="actGroup" theme="simple" list="groups" ></s:select><br /><br />
				Role: <s:select id="actRole" theme="simple" list="roles" ></s:select><br /><br />
				Number of actors: <input id="actActorCount" name="actActorCount" type="text" /><br /><br /> 
				<input type="button" value="Add to workflow" onclick="addActToWf2()" />
			</div>
		</div>
		<div style="margin-left: 600px;">
			<s:updownselect theme="simple" id="activities" name="activities" multiple="true" size="18" list="{}">
			</s:updownselect>
		</div>
		<input type="button" value="Done" onclick="submitForm()" />
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
			document.getElementById("activities").innerHTML="";
			document.getElementById("actName").value="";
		}
		
		function rbHandler()
		{
			d1.style.visibility="hidden";
			d1.style.height="0px";
			d2.style.height="600px";
			d2.style.visibility="visible";
			document.getElementById("activities").innerHTML="";
			document.getElementById("actName2").value="";
			document.getElementById("actActorCount").value="";
		}
		
		function addActToWf()
		{
			document.getElementById("activities").innerHTML+=
				"<option>"
				+document.getElementById("actName").value
				+"-"
				+document.getElementById("actActor").value
				+"</options>";
			document.getElementById("actName").value="";
		}
		
		function addActToWf2()
		{
			document.getElementById("activities").innerHTML+=
				"<option>"
				+document.getElementById("actName2").value
				+"-"
				+document.getElementById("actGroup").value
				+"-"
				+document.getElementById("actRole").value
				+"-"
				+document.getElementById("actActorCount").value
				+"</options>";
			document.getElementById("actName2").value="";
		}
		
		function submitForm()
		{
			var act=document.getElementById("activities");
			for(var i=0; i<act.options.length; i++)
				act.options[i].selected=true;
			document.getElementById("f1").submit();
		}
		
	</script>
	
</body>
</html>