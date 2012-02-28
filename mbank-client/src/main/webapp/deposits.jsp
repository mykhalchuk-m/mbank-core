<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page errorPage="/error-page.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="header-service.jsp">
	<jsp:param value="Deposits" name="title" />
</jsp:include>
<script type="text/javascript">
$(document).ready(function(){
	$(".deposit").validate({
		focusInvalid: false,
		focusCleanup: true,
		rules: {
			amount: {
				required: true,
				min: 0
			},
			closing: {
				required: true,
				customDate: true
			}
		},
		messages: {
			amount: {
				required: "can't be empty",
				min: "can't by less then 0"
			},
			closing: {
				required: "can't be empty",
				customDate: $.validator.messages.customDate
			}
		}
	});
	$.validator.addMethod("customDate",
		function(value, element) {
			re = /^(\d{1,2})\/(\d{1,2})\/(\d{4})$/;
			if (value != null) {
				if (regs = value.match(re)) {
					if (regs[1] < 1 || regs[1] > 31) {
						$.validator.messages.customDate = "Invalid value for day: " + regs[1];
						return false;
					}
					if(regs[2] < 1 || regs[2] > 12) {
						$.validator.messages.customDate = "Invalid value for month: " + regs[2];
						return false;
					}
					if(regs[3] < (new Date()).getFullYear()) {
						$.validator.messages.customDate = "Invalid value for year: " + regs[3] + " - must be great that " + (new Date()).getFullYear();
						return false;
					}
					var today = new Date();
					var closDate = new Date(regs[3], (regs[2] - 1), regs[1]);
					if (today - closDate > 0) {
						$.validator.messages.customDate = "Closing date must be in future";
						return false;
					}
				} else {
					$.validator.messages.customDate = "Invalid date format: " + value;
					return false;
				}
			}
			return true;
		}, "");
	    $(function() {
	        $('.date-pick').datePicker({autoFocusNextInput: true});            
	    });
});
</script>
</head>
<body>
	<div id="main">
		<jsp:include page="header.jsp">
			<jsp:param value="My Deposits" name="menu" />
		</jsp:include>
		<div id="site_content">
			<div class="sidebar"><jsp:include page="latest-activities.jsp" /></div>
			<div id="content">
				<h1>Your Deposit Information:</h1>
				<c:choose>
					<c:when test="${fatalErrors != null}">
						<h2>${fatalErrors}</h2>
					</c:when>
					<c:otherwise>
						<h2>Create new deposit:</h2>
						<form action="/mbank-client/deposits" method="post" class="deposit">
							<c:if test="${errors != null}">
								<ul class="error-content">
									<c:forEach items="${errors}" var="errorMessage">
										<li>${errorMessage}</li>
									</c:forEach>
								</ul>
							</c:if>
							<div class="form_settings">
								<p>
									<span>Deposit balance: </span><input type="text" name="amount"
										value="${param['amount']}"/>
								</p>
								<p>
									<span>Closing date: </span><input type="text" name="closing"
										value="${param['closing']}" class="date-pick dp-applied"/>
								</p>
								<p>
									<span>&nbsp;</span><input type="submit" value="Create Deposit"
										class="submit" /> <input type="hidden" name="id"
										value="${id}" />
								</p>
							</div>
						</form>
						<h2>Deposits</h2>
						<jsp:include page="deposits-table.jsp">
							<jsp:param value="${pagination}" name="pagination" />
							<jsp:param value="${currentPage}" name="currentPage" />
							<jsp:param value="${pageContext.request.requestURL}" name="url" />
							<jsp:param value="${id}" name="id" />
							<jsp:param value="${deposits}" name="deposits" />
						</jsp:include>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<jsp:include page="footer.jsp" />
	</div>
</body>
</html>