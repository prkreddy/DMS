<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>
</head>
<body>
	<h1 align="center">DOCUMENT MANAGEMENT SYSTEM</h1>
	<div align="center">
		<s:form action="userRegisterAction" autocomplete="off">

			<s:textfield key="username" label="Username" />
			<s:password key="password" label="Password" />
			<s:password key="confirmPassword" label="confirmPassword" />
			<s:textfield key="fullName" label="fullName"></s:textfield>
			<s:textfield key="emailid" label="email-Id" />
			<s:submit type="image" src="./images/newregister.gif" />
		</s:form>
		<p>
		<table align="center" cellspacing="10" cellpadding="10">
			<tr>
				<td><b>Already Existing User?</b></td>
				<td><a href="login"><img src="images/signinbutton.gif" /></a></td>
			</tr>
		</table>

	</div>


	<p>
	<div align="center">

		<img src="images/dmsregister.jpg" width="320" height="240" />
	</div>
</body>
</html>