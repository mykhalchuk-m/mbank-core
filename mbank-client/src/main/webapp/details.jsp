<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.epam.mbank.entities.ClientType"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page errorPage="/error-page.jsp" %>
<html>
<head>
<jsp:include page="header-service.jsp">
	<jsp:param value="My Details" name="title" />
</jsp:include>
</head>
<body>
	<div id="main">
		<jsp:include page="header.jsp">
			<jsp:param value="My Details" name="menu" />
		</jsp:include>
		<div id="site_content">
			<div class="sidebar"><jsp:include page="latest-activities.jsp" /></div>
			<div id="content">
				<h1>Your Personal Data:</h1>
				<script type="text/javascript">
				$(document).ready(function() {
					oldClient = null;
					canSave = false;
					$.ajax({
						type : "GET",
						dataType : "json",
						url : $("#edit").attr("action") + "?id="
								+ $("#edit input[name='id']").val()
								+ "&json=json",
						success : function(data) {
							oldClient = data;
						}
					});
				
					$(".invisible-input").attr("readonly", true);
					$(".invisible-input-select").attr("readonly", true);
				
					$(".editable").dblclick(function() {
						if (canSave) {
							$(this).removeAttr('readonly');
							$(this).removeClass("invisible-input");
							$(this).blur(function() {
								$(this).addClass("invisible-input");
								$(this).attr("readonly", true);
								if (autoSave) {
									save($(this).attr("name"), $(this).val());
								}
							});
						}
					});
				
				$(".form_settings .submit").addClass("hidden");
					$("#autosave").click (function() {
						$(".form_settings .submit").addClass("hidden");
						$(".form_settings .submit").removeClass("visible");
						$(".editable").css("border-bottom", "1px dotted #A4AA04");
						canSave = true;
						autoSave = true;
					});
					$("#manualsave").click (function() {
						$(".form_settings .submit").addClass("visible");
						$(".form_settings .submit").removeClass("hidden");
						$(".editable").css("border-bottom", "1px dotted #A4AA04");
						canSave = true;
						autoSave = false;
					});
				});
				function save(name, value) {
					if (oldClient[name] != value) {
						$.post($("#edit").attr("action"), $("#edit").serialize())
							.error(function(xhr, ajaxOptions, thrownError) {
								alert(xhr.responseText);
			                    alert(thrownError);
							});
						oldClient[name] = value;
					}
				}
				</script>

				<%
					pageContext.setAttribute("clientTypes", ClientType.values());
				%>

				<h2>Base Client Parameters:</h2>
				<h5 class="note">With double click on underlined field value you can edit it.
					Too, you can choose save parameter: with autosave exactly after the field will be
					changed or all changes together by pressing Edit Client button.</h5>
				<div class="save-settings">
					<input id="autosave" value="autosave" type="radio" name="saveSettings" /> <label>save
						changes one by one automatically</label> <input id="manualsave" value="manualsave"
						type="radio" name="saveSettings" /> <label>save all changes manual</label>
				</div>
				<form action="/mbank-client/details" method="post" id="edit">
					<c:if test="${errors != null}">
						<ul class="error-content">
							<c:forEach items="${errors}" var="errorMessage">
								<li>${errorMessage}</li>
							</c:forEach>
						</ul>
					</c:if>
					<div class="form_settings">
						<p>
							<span>User name: </span><input type="text" name="name" value="${client.name}"
								class="invisible-input">
						</p>
						<p>
							<span>User email: </span><input type="text" name="email" value="${client.email}"
								class="invisible-input editable" autocomplete="off">
						</p>
						<p>
							<span>User password: </span><input type="password" name="pass"
								value="${client.password}" class="invisible-input">
						</p>
						<p>
							<span>User type: </span> <input type="text" name="clientType"
								value="${client.clientType}" class="invisible-input-select visible"
								autocomplete="off">
						</p>
						<p>
							<span>User phone: </span><input type="text" name="phone" value="${client.phone}"
								class="invisible-input editable" autocomplete="off">
						</p>
						<p>
							<span>User address: </span><input type="text" name="address"
								value="${client.address}" class="invisible-input editable" autocomplete="off">
						</p>
						<p>
							<span>User comment: </span><input type="text" name="comment"
								value="${client.comment}" class="invisible-input" autocomplete="off">
						</p>
						<p>
							<input type="hidden" name="id" value="${client.id}" /> <span>&nbsp;</span><input
								class="submit" type="submit" value="Edit Client">
						</p>
					</div>
				</form>
				<h2>Client Account Parameters:</h2>
				<div>
					<div class="form_settings">
						<p>
							<span>Account balance:</span> <span>${client.account.balance}</span>
						</p>
						<p>
							<span>Account credit limit:</span>
							<c:choose>
								<c:when test="${client.account.creditLimit < 0}">
									<span>unlimited</span>
								</c:when>
								<c:otherwise>
									<span>${client.account.creditLimit}</span>
								</c:otherwise>
							</c:choose>
						</p>
						<p>
							<span>Account coment:</span> <span>${client.account.comment}</span>
						</p>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="footer.jsp" />
	</div>
</body>
</html>
