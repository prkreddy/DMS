<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
	<h1 align="middle">DOCUMENT MANAGEMENT SYSTEM</h1>

	<div align="center">
		<s:form action="userLoginAction" autocomplete="off">

			<s:textfield key="username" label="Username" />
			<s:password key="password" label="Password" />
			<s:submit label="login" />
		</s:form>


		<a href="signup.jsp"><b>Register</b></a>
	</div>
</body>
</html>