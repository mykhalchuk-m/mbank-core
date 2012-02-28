package com.epam.mbank.admin.filters;

import java.io.IOException;
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
import javax.servlet.http.HttpSession;

//@WebFilter(urlPatterns = "/*")
public class LoginFilter implements Filter {

	private FilterConfig filterConfig;
	private static String requestURI = null;
	private List<String> posUrls = new LinkedList<String>();

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
//		HttpServletRequest httpRequest = (HttpServletRequest) request;
//		requestURI = httpRequest.getRequestURI();
//		for (String posUrl : posUrls) {
//			if (requestURI.contains(posUrl)) {
//				chain.doFilter(request, response);
//				return;
//			}
//		}
//		requestURI = requestURI.endsWith("/limit-acts") ? null : requestURI;
//		HttpSession session = httpRequest.getSession();
//		Object loggined = session.getAttribute("loggined");
//		if (loggined == null || !Boolean.parseBoolean(loggined.toString())) {
//			if (session.getAttribute("requestURI") == null)
//				session.setAttribute("requestURI", requestURI);
//			filterConfig.getServletContext().getRequestDispatcher("/login").forward(request, response);
//			return;
//		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {
		this.filterConfig = config;
		posUrls.add("/login");
		posUrls.add("/resources/");
	}

}
