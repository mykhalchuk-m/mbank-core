<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="/error-page.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="header-service.jsp">
	<jsp:param value="My Settings" name="title" />
</jsp:include>
</head>
<body>
	<div id="main">
		<jsp:include page="header.jsp">
			<jsp:param value="Home" name="menu" />
		</jsp:include>
		<div id="site_content">
			<div class="sidebar"><jsp:include page="latest-activities.jsp" /></div>
			<div id="content">
				<h1>MBank system properties</h1>
				<div class="form_settings">
					<c:forEach items="${props}" var="prop">
						<p>
							<span>${prop.name}</span><span>${prop.value}</span>
						</p>
					</c:forEach>
				</div>
			</div>
		</div>
		<jsp:include page="footer.jsp" />
	</div>
</body>
</html>