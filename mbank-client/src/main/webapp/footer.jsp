<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="menu" class="com.epam.mbank.client.utils.Menu" scope="page"></jsp:useBean>
<div id="footer">
	<p>
		<c:forEach items="${menu.menu}" var="item">
			<a href="${item.value}">${item.key}</a> |
		</c:forEach>
	</p>
	<p>
		Copyright &copy; Maryan Mykhalchuk | <a
			href="http://validator.w3.org/check?uri=referer">HTML5</a> | <a
			href="http://jigsaw.w3.org/css-validator/check/referer">CSS</a> | <a
			href="http://www.html5webtemplates.co.uk">design from
			HTML5webtemplates.co.uk</a>
	</p>
</div>