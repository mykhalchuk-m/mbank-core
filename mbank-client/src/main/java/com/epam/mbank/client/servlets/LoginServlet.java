package com.epam.mbank.client.servlets;

import static com.epam.mbank.client.utils.LogMessages.*;
import static com.epam.mbank.client.utils.HttpVariable.*;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.mbank.entities.Client;
import com.epam.mbank.loggin.services.LogProducer;
import com.epam.mbank.services.LoginService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name = "login", urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	private final Logger logger = LoggerFactory.getLogger(getClass());
//
//	@EJB(mappedName = "LoginService/local")
//	private LoginService loginService;
//	@EJB(mappedName = "LogProducer/local")
//	private LogProducer logProducer;

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
//		logger.info(".[POST] logining with name: " + name + ", password: " + pass);
//		if (isEmplty(name) || isEmplty(pass)) {
//			logger.info(".[POST] Empty name or pass");
//			request.setAttribute(ERROR, "No no...you forgot somth., please try again later");
//			doGet(request, response);
//		} else {
//			Client client = loginService.clientLogin(name, pass);
//			if (client == null) {
//				logger.info(".[POST] Empty client for this name and pass");
//				request.setAttribute(ERROR, "No no...somthing wrong, please try again later");
//				doGet(request, response);
//			} else {
//				request.getSession().setAttribute(CLIENT, client);
//				response.sendRedirect(requestURI);
//				logProducer.registerLog(client.getId(), new StringBuilder().append(PREFIX).append(client.getId())
//						.append(FINISH_IN_SERVLET).append(this.getClass().getName()).append(POST).toString());
//			}
//		}
	}

	private boolean isEmplty(String arg) {
		return arg == null || arg == "";
	}

}
