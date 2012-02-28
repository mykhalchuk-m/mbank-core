<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${subTitle != null}">
	<h2>${subTitle}</h2>
</c:if>
<c:if test="${asuncTableLoad == 'true'}">
<script type="text/javascript">
	$(document).ready(function() {
		$(".pagination a").each(function() {
			$(this).click(function(event) {
				event.preventDefault();
				$.ajax({
					url: $(this).attr("href"),
					success: function(data) {
						$(".activities-table").html(data); 
					} 
				});
			});
		});
	});
</script>
<c:set var="url" value="/mbank-client/activities-table" scope="request" />
</c:if>
<div class="activities-table">
<table>
	<tr>
		<th>Client</th>
		<th>Amount</th>
		<th>Commission</th>
		<th>Activity Date</th>
		<th>Description</th>
	</tr>
	<c:forEach items="${activities}" var="activity">
		<tr>
			<td>${activity.client.name}</td>
			<td>${activity.amount}</td>
			<td>${activity.commission}</td>
			<td>${activity.activityDate}</td>
			<td>${activity.description}</td>
		</tr>
	</c:forEach>
</table>
<jsp:include page="pagination.jsp">
	<jsp:param value="${pagination}" name="pagination" />
	<jsp:param value="${currentPage}" name="currentPage" />
	<jsp:param value="${id}" name="id" />
</jsp:include>
</div>