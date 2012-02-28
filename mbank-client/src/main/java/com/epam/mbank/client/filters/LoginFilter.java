package com.epam.mbank.client.filters;

import java.io.IOException;
import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//@WebFilter(urlPatterns = "/*")
public class LoginFilter {/*implements Filter {
	private static String requestURI = null;
	private List<String> posUrls = new LinkedList<String>();

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// HttpServletRequest httpRequest = (HttpServletRequest) request;
		// requestURI = httpRequest.getRequestURI();
		// for (String posUrl : posUrls) {
		// if (requestURI.contains(posUrl)) {
		// chain.doFilter(request, response);
		// return;
		// }
		// }
		// requestURI = requestURI.endsWith("/limit-acts") ? "/mbank-client" :
		// requestURI;
		// HttpSession session = httpRequest.getSession();
		// if (httpRequest.getUserPrincipal() != null &&
		// session.getAttribute("logged") == null) {
		// session.setAttribute("logged", new Object());
		// HttpServletResponse httpResponse = (HttpServletResponse) response;
		// httpResponse.sendRedirect(requestURI);
		// }
		 chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
		posUrls.add("/login");
		posUrls.add("/resources/");
	}*/
}
