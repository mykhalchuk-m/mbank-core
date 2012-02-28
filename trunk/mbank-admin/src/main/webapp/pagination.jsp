<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="pagination">
	<c:forEach items="${pagination}" var="p">
		<span> <c:choose>
				<c:when test="${p != ''}">
					<c:choose>
						<c:when test="${p == currentPage}">
							<a href="${url}?id=${id}&p=${p}" class="current">${p}</a>
						</c:when>
						<c:otherwise>
							<a href="${url}?id=${id}&p=${p}">${p}</a>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>...</c:otherwise>
			</c:choose>
		</span>
	</c:forEach>
</div>