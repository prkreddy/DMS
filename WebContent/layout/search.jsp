<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form id="searchForm" action="userLoginAction" method="post">
		<input type="text" name="title" title="search documents by title name" />
		<a style="color:#412066;" href="javascript:{}" onclick="document.getElementById('searchForm').submit(); return false;"><b>Search</b></a>
	</form>
</body>
</html>
