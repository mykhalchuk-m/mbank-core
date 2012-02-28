<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="/error-page.jsp" %>
<html>
<head>
<jsp:include page="header-service.jsp">
	<jsp:param value="Login" name="title"/>
</jsp:include>
</head>
<script type="text/javascript">
$(document).ready(function(){
	$(".login").validate({
		focusInvalid: false,
		focusCleanup: true,
		rules: {
			username : {
				required: true,
			},
			password: {
				required: true,
			}
		},
		messages: {
			username: {
				required: "can't be empty"
			},
			password: {
				required: "can't be empty",
			}
		}
	});
});
</script>
<body>
	<div id="main">
		<jsp:include page="header.jsp">
			<jsp:param value="Login" name="menu"/>
		</jsp:include>
		<div id="site_content">
			<div class="sidebar"></div>
			<div id="content">
				<div class="form-wrapper">
				<form action="j_security_check" method="post" class="login">
					<h3>Login</h3>
					<label class="error">${error}</label>
					<div><label for="username">Username:</label><input type="text" name="j_username"/></div>
					<div><label for="password">Password:</label><input type="password" name="j_password"/></div>
					<div class="bottom">
						<input type="submit" value="Login"/>
					</div>
				</form>
				</div>
			</div>
		</div>
		<jsp:include page="footer.jsp" />
	</div>
</body>
</html>
