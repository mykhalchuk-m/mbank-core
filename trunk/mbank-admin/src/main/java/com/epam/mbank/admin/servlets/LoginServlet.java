package com.epam.mbank.admin.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.mbank.services.LoginService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name = "login", urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB(mappedName = "LoginService/local")
	private LoginService loginService;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
//		RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
//		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
//		Object requestURIobj = request.getSession().getAttribute("requestURI");
//		String requestURI = requestURIobj == null ? "/mbank-admin" : requestURIobj.toString();
//		String name = request.getParameter("username");
//		String pass = request.getParameter("password");
//		if (isEmplty(name) || isEmplty(pass)) {
//			request.setAttribute("loginErrorMessage", "No no...you forgot somth., please try again later");
//			doGet(request, response);
//		} else {
//			if (loginService.adminLogin(name, pass)) {
//				request.getSession().setAttribute("loggined", true);
//				response.sendRedirect(requestURI);
//				request.getSession().setAttribute("requestURI", null);
//			} else {
//				request.setAttribute("loginErrorMessage", "No no...somthing wrong, please try again later");
//				doGet(request, response);
//			}
//		}
	}

	private boolean isEmplty(String arg) {
		if (arg == null || arg == "") {
			return true;
		} else {
			return false;
		}
	}
}
