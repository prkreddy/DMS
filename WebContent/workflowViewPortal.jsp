<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript">
function uss(e)
{
	
	var req=new XMLHttpRequest();
	
	req.onreadystatechange=
		function()
		{
			if(req.readyState==4 && req.status==200)
			{
				var s=req.responseText;
				//alert(s);
				document.getElementById('d').innerHTML=s;
			}
		};
		
	req.open("GET", "getWorkflowDetails?name="+e.value+"&type=us", true);
	req.send();
	
	//for(var i=0; i<e.options.length; i++)
		//e.options[i].selected=false;
}

function rbs(e)
{
	var req=new XMLHttpRequest();
	
	req.onreadystatechange=
		function()
		{
			if(req.readyState==4 && req.status==200)
			{
				var s=req.responseText;
				//alert(s);
				document.getElementById('d').innerHTML=s;
			}
		};
		
	req.open("GET", "getWorkflowDetails?name="+e.value+"&type=rb", true);
	req.send();
	
	//for(var i=0; i<e.options.length; i++)
		//e.options[i].selected=false;
}
</script>

</head>
<body>

<h2>Workflows</h2>

<div style="float: left; width: 400px;">
	User-specific Workflows<br />
	<s:select list="us" multiple="true" size="10" theme="simple" style="width:300px;" onclick="uss(this)"></s:select>
</div>
<div>
	Role-based Workflows<br />
	<s:select list="rb" multiple="true" size="10" theme="simple" style="width:300px;" onclick="rbs(this)"></s:select>
</div>
<hr />
<div id="d">
</div>

</body>
</html>