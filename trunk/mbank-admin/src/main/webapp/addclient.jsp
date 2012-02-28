<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="header-service.jsp">
	<jsp:param value="Add Client" name="title" />
</jsp:include>
<script type="text/javascript">
$(document).ready(function(){
	$(".addclient").validate({
		focusInvalid: false,
		focusCleanup: true,
		rules: {
			name : {
				required: true,
			},
			email: {
				required: true,
				email: true
			},
			pass: {
				required: true,
				minlength: 6
			},
			balance: {
				required: true,
				number: true,
				min: 0				
			}
		},
		messages: {
			name: {
				required: "can't be empty"
			},
			email: {
				required: "can't be empty",
				email: "invalid input"
			},
			pass: {
				required: "can't be empty",
				minlength: "can't be less then {0} characters"
			},
			balance: {
				required: "can't be empty",
				number: "must by a number",
				min: "can't be negative"
			}
		}
	});
	$("input.phone").mask("(999) 999-9999");
});
</script>
</head>
<body>
	<div id="main">
		<jsp:include page="header.jsp">
			<jsp:param value="Clients" name="menu" />
		</jsp:include>
		<div id="site_content">
			<div class="sidebar"><jsp:include page="latest-activities.jsp" /></div>
			<div id="content">
				<h1>Register new client</h1>
				<form action="/mbank-admin/add-client" method="post" class="addclient">
					<c:if test="${requestScope['errors'] != null}">
						<ul class="error-content">
							<c:forEach items="${requestScope['errors']}" var="errorMessage">
								<li>${errorMessage}</li>
							</c:forEach>
						</ul>
					</c:if>
					<div class="form_settings">
					<p>
						<span>User name: </span><input type="text" name="name" value="${param['name']}">
					</p>
					<p>
						<span>User email: </span><input type="text" name="email"
							value="${param['email']}">
					</p>
					<p>
						<span>User password: </span><input type="password" name="pass"
							value="${param['pass']}">
					</p>
					<p>
						<span>User phone: </span><input type="text" name="phone" class="phone"
							value="${param['phone']}">
					</p>
					<p>
						<span>User address: </span><input type="text" name="address"
							value="${param['address']}">
					</p>
					<p>
						<span>User balance: </span><input type="text" name="balance"
							value="${param['balance']}">
					</p>
					<p>
						<span>User comment: </span><input type="text" name="comment"
							value="${param['comment']}">
					</p>
					<p><span>&nbsp;</span><input class="submit" type="submit" value="Create Client"></p>
					</div>
				</form>
			</div>
		</div>
		<jsp:include page="footer.jsp" />
	</div>
</body>
</html>