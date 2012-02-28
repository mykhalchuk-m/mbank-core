<%@page import="com.epam.mbank.entities.ClientType"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
					$(this).parent("form#edit").validate();
					save($(this).attr("name"), $(this).val());
				}
			});
		}
	});

	$(".invisible-input-select").dblclick(function() {
		if (canSave) {
			$(this).removeClass("visible");
			$(this).addClass("hidden");
			$(".invisible-select", $(this).parent()).removeClass("hidden");
			$(".invisible-select", $(this).parent()).addClass("visible");
			$(".invisible-select", $(this).parent()).focus();
			$(".invisible-select", $(this).parent()).blur(function() {
				$(this).removeClass("visible");
				$(this).addClass("hidden");
				$(".invisible-input-select").attr("value", $(this).val());
				$(".invisible-input-select").addClass("visible");
				$(".invisible-input-select").removeClass("hidden");
				if (autoSave) {
					save($(this).attr("name"), $(this).val());
				}
			});
			$(".invisible-select", $(this).parent()).change(function() {
				$(this).removeClass("visible");
				$(this).addClass("hidden");
				$(".invisible-input-select").attr("value", $(this).val());
				$(".invisible-input-select").addClass("visible");
				$(".invisible-input-select").removeClass("hidden");
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
</script>

<% pageContext.setAttribute("clientTypes", ClientType.values()); %>

<h2>Base Client Parameters:</h2>
<h5 class="note">With double click on underlined field value you
	can edit it. Too, you can choose save parameter: with autosave exactly
	after the field will be changed or all changes together by pressing
	Edit Client button.</h5>
<div class="save-settings">
	<input id="autosave" value="autosave" type="radio" name="saveSettings" />
	<label>save changes one by one automatically</label> <input
		id="manualsave" value="manualsave" type="radio" name="saveSettings" />
	<label>save all changes manual</label>
</div>
<form action="/mbank-admin/edit" method="post" id="edit">
	<c:if test="${requestScope['errors'] != null}">
		<ul class="error-content">
			<c:forEach items="${requestScope['errors']}" var="errorMessage">
				<li>${errorMessage}</li>
			</c:forEach>
		</ul>
	</c:if>
	<div class="form_settings">
		<p>
			<span>User name: </span><input type="text" name="name"
				value="${client.name}" class="invisible-input">
		</p>
		<p>
			<span>User email: </span><input type="text" name="email"
				value="${client.email}" class="invisible-input editable" autocomplete="off">
		</p>
		<p>
			<span>User password: </span><input type="password" name="pass"
				value="${client.password}" class="invisible-input">
		</p>
		<p>
			<span>User type: </span> <select name="clientType"
				class="invisible-select hidden">
				<c:forEach items="${clientTypes}" var="type">
					<c:choose>
						<c:when test="${type == client.clientType}">
							<option value="${type}" selected="selected">${type}</option>
						</c:when>
						<c:otherwise>
							<option value="${type}">${type}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select> <input type="text" name="clientType" value="${client.clientType}"
				class="invisible-input-select visible editable" autocomplete="off">

		</p>
		<p>
			<span>User phone: </span><input type="text" name="phone" 
				value="${client.phone}" class="invisible-input editable phone" autocomplete="off">
		</p>
		<p>
			<span>User address: </span><input type="text" name="address"
				value="${client.address}" class="invisible-input editable" autocomplete="off">
		</p>
		<p>
			<span>User comment: </span><input type="text" name="comment"
				value="${client.comment}" class="invisible-input editable" autocomplete="off">
		</p>
		<p>
			<input type="hidden" name="id" value="${client.id}" /> <span>&nbsp;</span><input
				class="submit" type="submit" value="Edit Client">
		</p>
	</div>
</form>
<h2>Client Account Parameters: </h2>
<div>
	<div class="form_settings">
		<p>
			<span>Account balance:</span>
			<span>${client.account.balance}</span>
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
			<span>Account coment:</span>
			<span>${client.account.comment}</span>
		</p>
	</div>
</div>