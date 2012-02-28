<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page errorPage="/error-page.jsp" %>
<html>
<head>
<jsp:include page="header-service.jsp">
	<jsp:param value="News" name="title" />
</jsp:include>
</head>
<body>
	<div id="main">
		<jsp:include page="header.jsp">
			<jsp:param value="Home" name="menu" />
		</jsp:include>
		<div id="site_content">
			<div class="sidebar"><jsp:include page="latest-activities.jsp" /></div>
			<div id="content">
				<c:import
					url="http://feeds.finance.yahoo.com/rss/2.0/headline?s=goog&region=US&lang=en-US"
					var="xml" />
				<x:parse xml="${xml}" varDom="dom"/>
				<h1><x:out select="$dom/rss/channel/title"/></h1>
				<h5><x:out select="$dom/rss/channel/lastBuildDate"/></h5>
				<ul>
					<x:forEach select="$dom/rss/channel/item" var="item">
						<li>
							<a href="<x:out select="$item/link"/>">
								<x:out select="$item/title"/>
							</a>
						</li>
					</x:forEach>
				</ul>
			</div>
		</div>
		<jsp:include page="footer.jsp" />
	</div>
</body>
</html>
