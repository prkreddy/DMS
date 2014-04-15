<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Success</title>
<script type="text/javascript">
	function applyFilter()
	{
		var req=new XMLHttpRequest();
		var t=document.getElementById('kws');
		
		req.onreadystatechange=
			function()
			{
				if(req.readyState==4 && req.status==200)
				{
					var s=req.responseText;
					document.getElementById('fragList1').innerHTML=s;
				}
			};
		req.open("POST", "filterAction?keywords=,"+t.value, true);
		req.send();
	}
	function checkEnter(event)
	{
		if(event.keyCode==13)
			applyFilter();
	}
	function populateWorkflowList()
	{
		var req=new XMLHttpRequest();
		var t=document.getElementById("documentTypeList");
		req.onreadystatechange=
			function()
			{
				if(req.readyState==4 && req.status==200)
				{
					document.getElementById('workflowList').innerHTML=req.responseText;
				}
			};
		req.open("POST", "populateWorkflowListAction?documentType="+t.value, true);
		req.send();
	}
</script>
</head>
<body>

	<s:form action="documentUploadAction" method="POST"
		enctype="multipart/form-data">
		<s:textfield key="documentName" name="documentName" id="documentName"
			label="Document name*" />
		<s:label value="1.0" label="Version"></s:label>
		<s:textarea key="description" name="description"
			label="Document description" cols="69" />
		<s:select onchange="populateWorkflowList()" id="documentTypeList" name="documentTypeList"
			list="docTypes" label="Document Type"></s:select>
		<s:select id="workflowList" name="workflowList"
			list="{}" label="Workflow"></s:select>
		<s:textfield key="keywords" name="keywords" id="keywords" size="70%"
			label="Keywords" />
		<s:label label="This document is*"></s:label>
		<s:checkbox id="isStandAlone" name="isStandAlone" label="standalone"
			value="1" onchange="validateFlags1()"></s:checkbox>
		<s:checkbox id="isReusable" name="isReusable" label="reusable"
			value="1" onchange="validateFlags2()"></s:checkbox>

		<s:radio id="dtype" name="dtype" onchange="typeChange()"
			label="Structure*"
			list="#{'1':'Single file document','2':'Compound document'}"></s:radio>

		<div align="center">
			<table id="documentSelection" align="center" cellpadding="20">
				<tr>
					<td colspan="3"><s:file id="nativeContentFile"
							name="uploadFile" size="40" theme="simple" /></td>
				</tr>
				<tr>
					<td>
						<div id='fltr' align="right">
							Keywords: <input type='text' onkeypress="checkEnter(event)" id='kws' autocomplete='off' title="Enter comma separated keywords" /><br />
							<input type="button" value="Apply Filter" onclick='applyFilter()' />
							<input type="button" value="Reset Filter" onclick="document.getElementById('kws').value=''; applyFilter();" />
						</div>
					</td>
				</tr>
				<tr>
					<td rowspan="4">
						<div style="float: left;">
							<s:select name="fragList1" cssStyle="width:300px;height:300px" id="fragList1"
								list="docFrags" multiple="true" size="5" theme="simple"></s:select>
						</div>
					</td>
					<td><div style="float: left;">
							<s:submit id='b1' value='>'
								onclick='moveSelectedOptions1(); return false;' theme="simple" />

						</div>
					</td>
					<td rowspan="4">
						<div>
							<s:select list="{}" name="fragsBeforeNativeContent" cssStyle="width:300px;height:300px"
								id="fragsBeforeNativeContent" size="5" multiple="true"
								theme="simple" />
							<s:submit id='up' value='^'
								onclick='moveSelectedOptionUp(); return false;' theme="simple" />
							<s:submit id='dn' value='v'
								onclick='moveSelectedOptionDn(); return false;' theme="simple" />
						</div>
					</td>
				</tr>

				<tr>
					<td><s:submit id='b2' value='>>'
							onclick='moveAllOptions1(); return false;' theme="simple" /></td>
				</tr>
				<tr>
					<td><s:submit id='b3' value='<' onclick='
							moveSelectedOptions1Back(); return false;'  theme="simple" /></td>
				</tr>
				<tr>
					<td><s:submit id='b4' value='<<' onclick='
							moveAllOptions1Back(); return false;'  theme="simple" /></td>
				</tr>
				<tr>
					<td colspan="3"><s:submit disabled="true" id="done"
							value="Done" onclick="selectAllDocsAndSubmit(); return false;" /></td>
				</tr>
			</table>
		</div>
	</s:form>
	<script type="text/javascript">
		var ncf = document.getElementById('nativeContentFile');
		var ncfp = ncf.parentNode;
		ncfp.removeChild(ncf);

		var fragList1 = document.getElementById('fragList1');
		var fragList1p = fragList1.parentNode;
		fragList1p.removeChild(fragList1);
		
		var fltr = document.getElementById('fltr');
		var fltrp = fltr.parentNode;
		fltrp.removeChild(fltr);

		var b1 = document.getElementById('b1');
		var b1p = b1.parentNode;
		b1p.removeChild(b1);

		var b2 = document.getElementById('b2');
		var b2p = b2.parentNode;
		b2p.removeChild(b2);

		var b3 = document.getElementById('b3');
		var b3p = b3.parentNode;
		b3p.removeChild(b3);

		var b4 = document.getElementById('b4');
		var b4p = b4.parentNode;
		b4p.removeChild(b4);

		var fragsBeforeNativeContent = document
				.getElementById('fragsBeforeNativeContent');
		var fragsBeforeNativeContentp = fragsBeforeNativeContent.parentNode;
		fragsBeforeNativeContentp.removeChild(fragsBeforeNativeContent);

		var up = document.getElementById('up');
		var upp = up.parentNode;
		upp.removeChild(up);

		var dn = document.getElementById('dn');
		var dnp = dn.parentNode;
		dnp.removeChild(dn);

		function typeChange() {
			document.getElementById('done').disabled = false;
			if (document.getElementsByName("dtype")[0].checked) {
				ncfp.appendChild(ncf);
				fragList1p.removeChild(fragList1);
				fltrp.removeChild(fltr);
				b1p.removeChild(b1);
				b2p.removeChild(b2);
				b3p.removeChild(b3);
				b4p.removeChild(b4);
				fragsBeforeNativeContentp.removeChild(fragsBeforeNativeContent);
				upp.removeChild(up);
				dnp.removeChild(dn);
			} else {
				fragList1p.appendChild(fragList1);
				fltrp.appendChild(fltr);
				b1p.appendChild(b1);
				b2p.appendChild(b2);
				b3p.appendChild(b3);
				b4p.appendChild(b4);
				fragsBeforeNativeContentp.appendChild(fragsBeforeNativeContent);
				upp.appendChild(up);
				dnp.appendChild(dn);
				ncfp.removeChild(ncf);
			}
		}
		function moveSelectedOptionUp() {
			var l1 = document.getElementById("fragsBeforeNativeContent");
			for (var i = 0; i < l1.options.length; i++)
				if (i > 0 && l1.options[i].selected == true
						&& l1.options[i - 1].selected == false) {
					l1.add(l1.options[i], l1[i - 1]);
					l1.options[i - 1].selected = true;
					l1.options[i].selected = false;
				}
		}
		function moveSelectedOptionDn() {
			var l1 = document.getElementById("fragsBeforeNativeContent");
			for (var i = l1.options.length - 1; i >= 0; i--)
				if (i < l1.options.length - 1 && l1.options[i].selected == true
						&& l1.options[i + 1].selected == false) {
					l1.add(l1.options[i + 1], l1[i]);
					l1.options[i].selected = false;
					l1.options[i + 1].selected = true;
				}
		}
		function validateFlags1() {
			if (!document.getElementById("isStandAlone").checked
					&& !document.getElementById("isReusable").checked) {
				document.getElementById("isStandAlone").checked = true;
				alert("Can't uncheck both at a time.");
			}
		}
		function validateFlags2() {
			if (!document.getElementById("isStandAlone").checked
					&& !document.getElementById("isReusable").checked) {
				document.getElementById("isReusable").checked = true;
				alert("Can't uncheck both at a time.");
			}
		}

		function selectAllDocsAndSubmit() {
			if (document.getElementById("documentName").value == "")
				alert('Please enter a document name.');
			else {
				if (document.getElementsByName("dtype")[0].checked) {
					if (document.getElementById("nativeContentFile").value == "")
						alert('Please choose a file.');
					else
						document.getElementById("frmDocEdit").submit();
				} else {
					var l1 = document
							.getElementById("fragsBeforeNativeContent");
					if (l1.options.length == 0)
						alert('Please choose a document.');
					else {
						for (var i = 0; i < l1.options.length; i++)
							l1.options[i].selected = true;
						document.getElementById("frmDocEdit").submit();
					}
				}
			}
		}

		function selectAllOptions1() {
			var e = document.getElementById('fragList1');
			for (var i = 0; i < e.options.length; i++)
				e.options[i].selected = true;
		}

		function moveSelectedOptions1() {
			var l1 = document.getElementById("fragList1");
			var l2 = document.getElementById("fragsBeforeNativeContent");
			var moveFlag = true;
			var msg = "";
			var ctr = 0;

			for (var i = l1.options.length - 1; i >= 0; i--)
				if (l1.options[i].selected) {
					moveFlag = true;
					for (var k = 0; k < l2.options.length; k++)
						if (l2.options[k].text.split(",")[0] == l1.options[i].text
								.split(",")[0]) {
							moveFlag = false;
							msg += (++ctr) + ". " + l1.options[i].text + "\n";
							break;
						}

					if (moveFlag)
						l2.options.add(l1.options[i]);
				}

			if (msg != "")
				alert("The following document(s) were not moved as some version of them already exists in or has beed moved to either list:\n\n"
						+ msg);
		}

		function moveAllOptions1() {
			selectAllOptions1();
			moveSelectedOptions1();
		}

		function selectAllOptions1Back() {
			var e = document.getElementById('fragsBeforeNativeContent');
			for (var i = 0; i < e.options.length; i++)
				e.options[i].selected = true;
		}

		function moveSelectedOptions1Back() {
			var l1 = document.getElementById("fragList1");
			var l2 = document.getElementById("fragsBeforeNativeContent");

			for (var i = l2.options.length - 1; i >= 0; i--)
				if (l2.options[i].selected) {
					l1.options.add(l2.options[i]);
				}
		}

		function moveAllOptions1Back() {
			selectAllOptions1Back();
			moveSelectedOptions1Back();
		}
	</script>
</body>
</html>