<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="menu" class="com.epam.mbank.admin.utils.Menu" scope="page"></jsp:useBean>
<div id="confirm">
	<div class="opasity"></div>
	<div class="confirm-content">
		<span>${param['confirmText']}</span>
		<div>
			<a href="#" class="yes">YES</a>
			<a href="#" class="no">NO</a>
		</div>
	</div>
</div>  
<div id="header">
	<div id="logo">
		<div id="logo_text">
			<!-- class="logo_colour", allows you to change the colour of the text -->
			<h1>
				<a href="/mbank-admin">MBank<span class="logo_colour">-admin</span></a>
			</h1>
			<h2>CDP project. Bank emulation system.</h2>
		</div>
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