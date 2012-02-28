<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page errorPage="/error-page.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="header-service.jsp">
	<jsp:param value="Activities" name="title" />
</jsp:include>
<script type="text/javascript">
$(document).ready(function(){
	$(".activity").validate({
		focusInvalid: false,
		focusCleanup: true,
		rules: {
			amount : {
				required: true,
				number: true,
				min: 0
			}
		},
		messages: {
			amount: {
				required: "can't be empty",
				number: "must be a numder",
				min: "can't be negative"
			}
		}
	});
});
</script>
</head>
<body>
	<div id="confirm">
		<div class="opasity"></div>
		<div class="confirm-content">
			<span>Commission for operation ${commission}$. Do you want continue?</span>
			<div>
				<a href="#" class="yes">YES</a>
				<a href="#" class="no">NO</a>
			</div>
		</div>
	</div> 
	<div id="main">
		<jsp:include page="header.jsp">
			<jsp:param value="My Activities" name="menu" />
		</jsp:include>
		<div id="site_content">
			<div class="sidebar"><jsp:include page="latest-activities.jsp" /></div>
			<div id="content">
				<h1>Your Activities in MBank system:</h1>
				<h2>Do not deny anything for yourself :)</h2>
				<div>
					<h3>My current balance: ${client.account.balance}</h3>
				</div>
				<form action="/mbank-client/activities" method="post" class="activity">
					<c:if test="${error != null}">
						<div style="color: #f00">${error}</div>
					</c:if>
					<div class="form_settings">
						<p>
							<input name="amount" type="text" value="" />
						</p>
						<p>
							<input type="submit" name="action" value="withdraw" id="withdraw"
								class="submit" /> <input type="submit" name="action"
								value="deposit" class="submit" />
						</p>
						<input type="hidden" name="id" value="${client.id}" />
					</div>
				</form>
				<h2>It is what have you done:</h2>
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
