<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><tiles:insertAttribute name="title" ignore="true" /></title>
</head>

<body style="background-color:#f6f4ff; font-family: cursive;">
   <tiles:insertAttribute name="banner" />
   
   <table width="100%" height="50px" style="background-color:#afafff;">
		<tr>
			<td><tiles:insertAttribute name="search" /></td>
			<td><tiles:insertAttribute name="home" /></td>
		</tr>
   </table>
   <br />
 
   <div style="margin-left:250px;">
   		<tiles:insertAttribute name="body" />
   </div>   
</body>
</html>
