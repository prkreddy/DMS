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
	<br />
	<!-- 	<a href="documentCreateAction">Create new document</a> |
	<a href="documentSelectForEditAction">Edit existing document</a> -->
	<br />
	<br />
	<table border="1" align="center">
		<thead>
			<tr>
				<td><b>View</b></td>
				<td><b>Name</b></td>
				<td><b>Version</b></td>
				<td><b>Access</b></td>
				<td><b>Date Created</b></td>
				<td><b>Date Modified</b></td>
				<td><b>Actor</b></td>
				<!-- <td><b>Size</b></td> -->
			</tr>
		</thead>
		<s:iterator value="docFragmentDisplayDetailsList"
			var="docFragmentDisplayDetails" status="stat">
			<tr>

				<td><button id='<s:property value="docId" />'
						value='<s:property value="name" />'>view</button>
				<td><s:property value="name" /></td>

				<td><s:property value="version" /></td>
				<td><s:property value="access" /></td>
				<td><s:property value="dateCreated" /></td>
				<td><s:property value="dateModified" /></td>
				<td><s:property value="actor" /></td>
				<%-- <td><s:property value="size" /></td> --%>
			</tr>
		</s:iterator>
	</table>


	<script src="js/jquery-1.10.2.js"></script>

	<script>
		//When DOM loaded we attach click event to button
		$(document).ready(
				function() {

					//after button is clicked we download the data
					$("button").click(
							function() {

								var docId = $(this).attr('id');

								//start ajax request
								$.post(
										"fileDownloadAction?documentId="
												+ docId, function(data) {
											window.open('finalNew.pdf');

										});
							});
				});
	</script>

</body>
</html>