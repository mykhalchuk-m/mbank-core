<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="header-service.jsp">
	<jsp:param value="Activities" name="title" />
</jsp:include>
</head>
<body>
	<div id="main">
		<jsp:include page="header.jsp">
			<jsp:param value="Activities" name="menu" />
		</jsp:include>
		<div id="site_content">
			<div class="sidebar"><jsp:include page="latest-activities.jsp"/></div>
			<div id="content">
				<h1>Activities of MBank's clients</h1>
				<jsp:include page="activities-table.jsp">
					<jsp:param value="${pagination}" name="pagination" />
					<jsp:param value="${currentPage}" name="currentPage" />
					<jsp:param value="${pageContext.request.requestURL}" name="url" />
					<jsp:param value="${id}" name="id" />
					<jsp:param value="${activities}" name="activities" />
				</jsp:include>
			</div>
		</div>
		<jsp:include page="footer.jsp" />
	</div>
</body>
</html>
