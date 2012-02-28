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

import com.epam.mbank.admin.utils.BuildJson;
import com.epam.mbank.entities.Client;
import com.epam.mbank.exception.NoSuchItem;
import com.epam.mbank.services.ClientService;

/**
 * Servlet implementation class ClientDetailsServlet
 */
@WebServlet(name = "client-details", urlPatterns = { "/client-details" })
public class ClientDetailsServlet extends HttpServlet {
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
		logger.info(".[GET] Get client details");
		Long id = Long.parseLong(request.getParameter("id"));
		try {
			Client client = clientService.getClient(id);
			String json = request.getParameter("json");
			if (json != null) {
				response.getWriter().write(BuildJson.buildJsonFromClient(client));
				return;
			}
			request.setAttribute("client", client);
			request.setAttribute("id", id);
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/client-details.jsp");
			rd.forward(request, response);
		} catch (NoSuchItem e) {
			logger.error(e.getMessage());
			request.setAttribute("errorMessage", e.getMessage());
		}
	}

}
