<%@page import="com.epam.mbank.entities.ClientType"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="header-service.jsp">
	<jsp:param value="Client Details" name="title" />
</jsp:include>
</head>
<body>
	<%
		pageContext.setAttribute("clientTypes", ClientType.values());
	%>
	<script type="text/javascript">
		$(document).ready(function() {
			$( "#tabs" ).tabs({
				ajaxOptions: {
					error: function( xhr, status, index, anchor ) {
						$(anchor.hash).html(
							"Couldn't load this tab. We'll try to fix this as soon as possible. " +
							"If this wouldn't be a demo." );
					}
				}
			});
		});
	</script>
	<div id="main">
		<jsp:include page="header.jsp">
			<jsp:param value="Clients" name="menu" />
		</jsp:include>
		<div id="site_content">
			<div class="sidebar"><jsp:include page="latest-activities.jsp" /></div>
			<div id="content">
				<h1>View Client Details:</h1>
				<div id=tabs>
					<ul>
						<li><a href="/mbank-admin/client-details?id=${id}">Basic Details</a></li>
						<li><a href="/mbank-admin/deposits-table?id=${id}">Deposits Details</a></li>
						<li><a href="/mbank-admin/activities-table?id=${id}">Activities Details</a></li>	
					</ul>
				</div>
			</div>
		</div>
		<jsp:include page="footer.jsp" />
	</div>
</body>
</html>