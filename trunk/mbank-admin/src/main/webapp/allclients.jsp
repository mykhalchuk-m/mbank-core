<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="header-service.jsp">
	<jsp:param value="All Clients" name="title" />
</jsp:include>
<body>
	<div id="main">
		<jsp:include page="header.jsp">
			<jsp:param value="Clients" name="menu" />
			<jsp:param value="Are you sure you want to delete this client" name="confirmText" />
		</jsp:include>
		<div id="site_content">
			<div class="sidebar"><jsp:include page="latest-activities.jsp" /></div>
			<div id="content">
				<div class="content-header" style="text-align: right; font-weight: bold;">
					<h1>All MBank's clients</h1>
					<h2>
						<a href="/mbank-admin/add-client">Add Client</a>
					</h2>
				</div>
				<c:if test="${!empty errorMessage}">
					<div style="color: #f00">
						<p>${errorMessage}</p>
					</div>
				</c:if>
				<table width="100%" class="clickable">
					<tr>
						<th>Name</th>
						<th>Client Type</th>
						<th>Email</th>
						<th>Phone</th>
						<!-- <th>Balance</th> -->
						<th>Address</th>
						<th>Comment</th>
						<!-- <th>Active deposits</th>  -->
						<th></th>
					</tr>
					<c:forEach items="${clients}" var="client">
						<tr>
							<td>${client.name}</td>
							<td>${client.clientType}</td>
							<td>${client.email}</td>
							<td>${client.phone}</td>
							<td>${client.address}</td>
							<td>${client.comment}</td>
							<td id="action"><a href="#" class="action-link"></a>
								<div class="action-container">
									<ul>
										<li><a href="/mbank-admin/deposits?id=${client.id}">deposits</a></li>
										<li><a href="/mbank-admin/activities?id=${client.id}">activities</a></li>
										<li id="removeClient"><a href="/mbank-admin/remove?id=${client.id}">remove client</a></li>
										<li><a href="/mbank-admin/edit?id=${client.id}">view details</a></li>
									</ul>
								</div>
							</td>
							<td class="hidden"><a href="/mbank-admin/edit?id=${client.id}"></a></td>
						</tr>
					</c:forEach>
				</table>
				<jsp:include page="pagination.jsp">
					<jsp:param value="${pagination}" name="pagination"/>
					<jsp:param value="${currentPage}" name="currentPage"/>
					<jsp:param value="${pageContext.request.requestURI}" name="url"/>
				</jsp:include>
			</div>
		</div>
		<jsp:include page="footer.jsp" />
	</div>
</body>
</html>