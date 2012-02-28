<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<jsp:include page="header-service.jsp">
	<jsp:param value="Error page" name="title" />
</jsp:include>
</head>
<body>
	<div id="main">
		<jsp:include page="header.jsp">
			<jsp:param value="Hom" name="menu" />
		</jsp:include>
		<div id="site_content">
			<div class="sidebar"></div>
			<div id="content">
				<div class="form-wrapper">
					<h4>Invalid url path.</h4>
				</div>
			</div>
		</div>
		<jsp:include page="footer.jsp" />
	</div>
</body>
</html>
