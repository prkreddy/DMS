<%@page import="java.util.List"%>
<%@page import="com.iiitb.model.DocFragmentDisplayDetails"%>
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
					document.getElementById('div55').innerHTML=s;
				}
			};
		req.open("POST", "filterAction2?keywords=,"+t.value, true);
		req.send();
	}
	function checkEnter(event)
	{
		if(event.keyCode==13)
			applyFilter();
	}
	function populatemd(docId)
	{
		var req=new XMLHttpRequest();
		req.onreadystatechange=
			function()
			{
				if(req.readyState==4 && req.status==200)
				{
					var s=req.responseText;
					document.getElementById('md').style.visibility="visible";
					document.getElementById('md2').innerHTML=s;
				}
			};
		req.open("POST", "metadataAction?docId="+docId, true);
		req.send();
	}
</script>
</head>
<body>
	<div id='md' style="position: absolute; width: 99%; height: 70%; left: .5%; top: 170px; background-color: white; visibility: hidden; border: thin; border-color: #bbbbbb; border-style: solid;">
		<div align="right" style="width: 100%;">
			<input type="button" value="X" onclick="document.getElementById('md').style.visibility='hidden';"/>
			<hr />
		</div>
		<div id='md2'>
		</div>
	</div>
	
	
	<br />
	<div id='fltr'>
		Keywords: <input type='text' onkeypress="checkEnter(event)" id='kws' autocomplete='off' title="Enter comma separated keywords" /><br />
		<input type="button" value="Apply Filter" onclick='applyFilter()' />
		<input type="button" value="Reset Filter" onclick="document.getElementById('kws').value=''; applyFilter();" />
		<hr />
	</div>

	<div id='div55'>
		<table border="1" align="center" cellpadding="10">
			<thead>
				<tr>
					<td><b>S.No</b></td>
					<td><b>View</b></td>
					<td><b id="order">Name</b></td>
					<td><b>Version</b></td>
					<td><b>Metadata</b></td>
					<td><b id="order">Date Created</b></td>
					<td><b id="order">Date Modified</b></td>
					<td><b>Created By</b></td>
					<s:if test="#session.user.getRole().getName()!='admin'">
						<td><b>Update Workflow Stage</b></td>
					</s:if>
					<!-- <td><b>Size</b></td> -->
				</tr>
			</thead>
			<s:iterator value="docFragmentDisplayDetailsList"
				var="docFragmentDisplayDetails" status="stat">
				<tr>
	
					<td><s:property value="%{#stat.index + 1}" /></td>
					<td><button id='<s:property value="docId" />'
							value='<s:property value="name" />'
							onclick="documentPreview(this)">preview</button>
					<td><s:property value="name" /></td>
	
					<td><s:property value='version.replace("-","")' /></td>
					<td><input type="button" onclick="populatemd('<s:property value="name" />, v<s:property value='version.replace("-","")' />')" value="view" /></td>
					<td><s:property value="dateCreated" /></td>
					<td><s:property value="dateModified" /></td>
					<td><s:property value="actor" /></td>
					<%-- <td><s:property value="size" /></td> --%>
					<s:if test="#session.user.getRole().getName()!='admin'">
						<td>
							<%boolean flag=true; %>
							<s:if test='version.contains("-")'>
								<s:if test='enableActivityUpdate == "true"'>
									<button id='update$<s:property value="docId" />'
										onclick="updateActivity(this)">update</button>
								</s:if> <s:elseif test='enableActivityUpdate == "false"'>
									<button id='update$<s:property value="docId" />'
										onclick="updateActivity(this)" disabled="disabled">update</button>
								</s:elseif>
							</s:if>
							<s:elseif test='version.equals("1.0")'>
								<%
									flag=true;
									List<DocFragmentDisplayDetails> l=(List<DocFragmentDisplayDetails>)request.getAttribute("docFragmentDisplayDetailsList");
									for(DocFragmentDisplayDetails dfdd:l)
									{
										if(dfdd.getName().equals(request.getAttribute("name").toString())
												&& !dfdd.getVersion().equals("1.0"))
										{
											flag=false;
											break;
										}
									}
									if(flag) {%>
										<s:if test='enableActivityUpdate == "true"'>
											<button id='update$<s:property value="docId" />'
												onclick="updateActivity(this)">update</button>
										</s:if> <s:elseif test='enableActivityUpdate == "false"'>
											<button id='update$<s:property value="docId" />'
												onclick="updateActivity(this)" disabled="disabled">update</button>
										</s:elseif>
									<%} else {%>
										
											<button id='update$<s:property value="docId" />'
												onclick="updateActivity(this)" disabled="disabled">update</button>
										
									<%}
								%>
							</s:elseif>
							<s:else>
							<button id='update$<s:property value="docId" />'
												onclick="updateActivity(this)" disabled="disabled">update</button>
							</s:else>
							
							
						</td>
					</s:if>
	
				</tr>
			</s:iterator>
		</table>
	</div>

	<script src="js/jquery-1.10.2.js"></script>

	<script>
		function documentPreview(data) {

			var docId = data.getAttribute("id");
		
				window.open("fileDownloadAction?documentId=" + docId, '_blank');
				//window.open("fileDownloadAction?documentId=" + docId,'popUpWindow','height=500,width=900,left=100,top=100,resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no, status=yes') ;

	

		}

		function updateActivity(data) {

			var docId = data.getAttribute("id");
			var split = docId.split("$");
			$.post("updateStatusAction?documentId=" + split[1],
					function(value) {
						var element = document.getElementById(docId);

						element.setAttribute("disabled", "disabled");

					});

		}

		/* $(document)
				.ready(
						function() {

							$("button").click(
									function() {

										var docId = $(this).attr('id');

										$.post("fileDownloadAction?documentId="
												+ docId, function(data) {
											window.open('finalNew.pdf',
													'_blank');

										});
									});

							$("input")
									.click(
											function() {

												var docId = $(this).attr('id');
												alert(docId);
												var split = docId.split("$");
												alert(split[1]);
												$
														.post(
																"updateStatusAction?documentId="
																		+ split[1],
																function(data) {
																	document
																			.getElementById(docId).disabled = true;
																});

											});

							//$(this).attr('disabled', 'disabled');
							//document.getElementsByTagName("input").disabled=true;

						}); */
	</script>

</body>
</html>
