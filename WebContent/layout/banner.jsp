<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table width="100%" bgcolor="#7066b6">
		<tr>
			<td><h3 align="left" style="color: WHITE; margin: 10px;">USER
					DOMAIN</h3></td>
			<td><h4 align="right" style="color: WHITE;">
					<s:property value="#session.user.name" />
					| <a style="color: WHITE;" href="userLogoutAction">Logout</a>
				</h4></td>
		</tr>
	</table>
</body>
</html>