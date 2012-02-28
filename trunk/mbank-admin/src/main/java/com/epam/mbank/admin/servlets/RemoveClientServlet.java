package com.epam.mbank.admin.servlets;

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

import com.epam.mbank.exception.NoSuchItem;
import com.epam.mbank.services.ClientService;

/**
 * Servlet implementation class RemoveClient
 */
@WebServlet("/remove")
public class RemoveClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@EJB(mappedName = "ClientService/local")
	private ClientService clientService;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			clientService.removeClient(clientService.getClient(id));
			response.sendRedirect("/mbank-admin/clients");
		} catch (NoSuchItem e) {
			logger.error(e.getMessage());
			request.setAttribute("errorMessage", e.getMessage());
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/clients");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
