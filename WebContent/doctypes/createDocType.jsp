<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body onload="unselect();">

	<s:form id="formAddDoctype" action="docTypeCreateAction" method="post">

		<table cellspacing="10">
			<tr>
				<td colspan="3"><s:textfield name="docTypeName"
						id="docTypeName" label="DocType Name" requiredLabel="true" /></td>
			</tr>
			<tr></tr>
			<tr></tr>


			<tr>
				<td><b>roleWorkflows</b></td>
				<td><s:select list="roleWorkflows" id="roleWorkflows"
						key="roleWorkflows" name="roleWorkflows" theme="simple"
						multiple="true" size="5" cssStyle="width:300px;height:200px" /></td>
				<td rowspan="2"><input type="button" value=">"
					onclick="addRoleWorkFlow();  " /><br> <input type="button"
					value="<"
					onclick=" removeWorkflow();"/></td>

				<td rowspan="2"><b>workflow's Selected</b><br><s:select list="{}" name="workflowsSelected"
						id="workflowsSelected" theme="simple" multiple="true" size="5"
						cssStyle="width:300px;height:200px" /></td>
			</tr>


			<tr>
				<td><b>userworkflows</b></td>
				<td><s:select list="userworkflows" id="userworkflows"
						key="userworkflows" name="userworkflows" theme="simple"
						multiple="true" size="5" cssStyle="width:300px;height:200px" /></td>
			</tr>
			<!-- <tr>

				<td><input type="button" value="Adduserworkflows"
					onclick="adduserworkflows();  "></input></td>

			</tr> -->
			<tr>
				<td><input id="submitform" type="button" value="Done"
					onclick="selectAll()" /></td>
			</tr>
		</table>
	</s:form>


	<script>
		function addRoleWorkFlow() {
			var rolesSelectList = document.getElementById("roleWorkflows");

			var workflowselectedList = document
					.getElementById("workflowsSelected");
			var length = rolesSelectList.options.length;
			for (var i = length - 1; i >= 0; i--) {

				if (rolesSelectList.options[i].selected) {
					workflowselectedList.innerHTML += "<option>role-"
							+ rolesSelectList.options[i].value + "</option>";

					rolesSelectList.remove(i);
				}

			}

			var userSpecificwflist = document.getElementById("userworkflows");

			var length = userSpecificwflist.options.length;
			for (var i = length - 1; i >= 0; i--) {

				if (userSpecificwflist.options[i].selected) {
					workflowselectedList.innerHTML += "<option>user-"
							+ userSpecificwflist.options[i].value + "</option>";
					userSpecificwflist.remove(i);
				}

			}

			return false;

		}

		function removeWorkflow() {
			var workflowselectedList = document
					.getElementById("workflowsSelected");

			var rolesSelectList = document.getElementById("roleWorkflows");

			var userSpecificwflist = document.getElementById("userworkflows");

			var leng = workflowselectedList.options.length;

			for (var i = leng - 1; i >= 0; i++) {

				if (workflowselectedList.options[i].selected) {

					var value = workflowselectedList.options[i].value;

					if ("user" == value.split("-")[0]) {

						userSpecificwflist.innerHTML += "<option>"
								+ value.split("-")[1] + "</option>";

					} else if ("role" == value.split("-")[0]) {
						rolesSelectList.innerHTML += "<option>"
								+ value.split("-")[1] + "</option>";
					}

					workflowselectedList.remove(i);

				}
			}

		}

		var role = document.getElementById("roleWorkflows");

		var user = document.getElementById("userworkflows");

		for (var i = 0; i < role.options.length; i++) {
			role.options[i].selected = false;
		}

		for (var i = 0; i < user.options.length; i++) {
			user.options[i].selected = false;
		}

		function selectAll() {

			var docName = document.getElementById("docTypeName");
			var msg = "";

			if (docName.value == "" || docName.value == null) {
				msg += "docTypeName is empty\n";
			}

			var workflowselectedList = document
					.getElementById("workflowsSelected");

			len = workflowselectedList.options.length;

			if (len == 0) {
				msg += "workflowselectedList is empty\n";
			}
			for (var i = 0; i < len; i++) {
				workflowselectedList.options[i].selected = true;
			}

			var formAddDoctype = document.getElementById("formAddDoctype");

			if (msg != "") {
				alert(msg);

			} else {
				formAddDoctype.submit();

			}

		}
	</script>
</body>
</html>