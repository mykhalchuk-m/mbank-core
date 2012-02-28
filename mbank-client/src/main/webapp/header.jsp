<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="menu" class="com.epam.mbank.client.utils.Menu" scope="page"></jsp:useBean>
<div id="header">
	<div id="logo">
		<div id="logo_text">
			<h1>
				<a href="/mbank-client">MBank<span class="logo_colour">-client</span></a>
			</h1>
			<h2>CDP project. Bank emulation system.</h2>
		</div>
		<div class=logout>[<a href="/mbank-client/logout">Logout</a>]</div>
	</div>
	<div id="menubar">
		<ul id="menu">
			<c:if test="${param['menu'] != 'Login'}">
				<c:forEach items="${menu.menu}" var="item">
					<c:choose>
						<c:when test="${item.key == param['menu']}">
							<li class="selected">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${item.value}">${item.key}</a>
					</li>
				</c:forEach>
			</c:if>
		</ul>
	</div>
</div>