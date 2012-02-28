<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="header-service.jsp">
	<jsp:param value="Accounts" name="title"/>
</jsp:include>
</head>
<body>
	<div id="main">
		<jsp:include page="header.jsp">
			<jsp:param value="Accounts" name="menu" />
		</jsp:include>
		<div id="site_content">
			<div class="sidebar"><jsp:include page="latest-activities.jsp"/></div>
			<div id="content">
				<h1>All MBank's accounts</h1>
				<table>
					<tr>
						<th>Client</th>
						<th>Balance</th>
						<th>Comment</th>
						<th>Credit limit</th>
					</tr>
					<c:forEach items="${requestScope['accounts']}" var="account">
						<tr>
							<td>${account.client.name}</td>
							<td>${account.balance}</td>
							<td>${account.comment}</td>
							<c:choose>
								<c:when test="${account.creditLimit > 0}">
									<td>${account.creditLimit}</td>
								</c:when>
								<c:otherwise>
									<td>unlim</td>
								</c:otherwise>
							</c:choose>
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