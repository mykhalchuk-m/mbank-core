<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="/error-page.jsp" %>
<html>
<head>
<jsp:include page="header-service.jsp">
	<jsp:param value="All Clients" name="title" />
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
				<h1>Welcome to the MBank system.</h1>
				<p>Here should by something about this system..</p>
				<p>
					You can view Mbank system properties. Go to <a
						href="/mbank-client/props">link</a> to do this.
				</p>
			</div>
		</div>
		<jsp:include page="footer.jsp" />
	</div>
</body>
</html>
