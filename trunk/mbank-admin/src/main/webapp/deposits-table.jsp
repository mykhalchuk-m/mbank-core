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
						$(".deposits-table").html(data); 
					} 
				});
			});
		});
	});
</script>
<c:set var="url" value="/mbank-admin/deposits-table" scope="request" />
</c:if>
<div class="deposits-table">
	<table>
		<tr>
			<th>Client</th>
			<th>Balance</th>
			<th>Opening Date</th>
			<th>Closing Date</th>
			<th>Type</th>
			<th>Estimated Balance</th>
		</tr>
		<c:forEach items="${deposits}" var="deposit">
			<tr>
				<td>${deposit.client.name}</td>
				<td>${deposit.balance}</td>
				<td>${deposit.openingDate}</td>
				<td>${deposit.closingDate}</td>
				<td>${deposit.type}</td>
				<td>${deposit.estimatedBalance}</td>
			</tr>
		</c:forEach>
	</table>
	<jsp:include page="pagination.jsp">
		<jsp:param value="${pagination}" name="pagination" />
		<jsp:param value="${currentPage}" name="currentPage" />
		<jsp:param value="${id}" name="id" />
	</jsp:include>
</div>