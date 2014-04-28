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
</head>
<body>
	<br />
	<div align="center">
		<p>#descending Order of ModifiedDate</p>
	</div>

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
				<td><b>Actor</b></td>
				<s:if test="#session.user.getRole().getName()!='admin'">
					<td><b>UpdateActivity</b></td>
				</s:if>
				<!-- <td><b>Size</b></td> -->
			</tr>
		</thead>
		<s:iterator value="docFragmentDisplayDetailsList"
			var="docFragmentDisplayDetails" status="stat">
			<tr>

				<td><s:property value="#stat.index" /></td>
				<td><button id='<s:property value="docId" />'
						value='<s:property value="name" />'
						onclick="documentPreview(this)">preview</button>
				<td><s:property value="name" /></td>

				<td><s:property value='version.replace("-","")' /></td>
				<td><a href='metadataAction?docId=<s:property value="name" />, v<s:property value='version.replace("-","")' />'>View</a></td>
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


	<script src="js/jquery-1.10.2.js"></script>

	<script>
		function documentPreview(data) {

			var docId = data.getAttribute("id");
			$.post("fileDownloadAction?documentId=" + docId, function(value) {
				window.open('finalNew.pdf', '_blank');

			});

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