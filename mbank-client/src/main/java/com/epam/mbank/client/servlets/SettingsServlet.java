package com.epam.mbank.client.servlets;

import static com.epam.mbank.client.utils.LogMessages.FINISH_IN_SERVLET;
import static com.epam.mbank.client.utils.LogMessages.GET;
import static com.epam.mbank.client.utils.LogMessages.PREFIX;
import static com.epam.mbank.client.utils.LogMessages.START_IN_SERVLET;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.mbank.dao.PropertiesManager;
import com.epam.mbank.entities.Property;
import com.epam.mbank.loggin.services.LogProducer;
import com.epam.mbank.services.ClientService;

/**
 * Servlet implementation class SettingsServlet
 */
@WebServlet("/props")
public class SettingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB(mappedName = "PropertiesManager/local")
	private PropertiesManager propertiesManager;

	@EJB(mappedName = "LogProducer/local")
	private LogProducer logProducer;
	
	@EJB(mappedName = "ClientService/local")
	private ClientService clientService;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		Long id = clientService.getClient(((Principal) request.getUserPrincipal()).getName()).getId();
		logProducer.registerLog(id,
				new StringBuilder().append(PREFIX).append(START_IN_SERVLET).append(this.getClass().getName())
						.append(GET).toString());
		List<Property> properties = propertiesManager.getAll();
		request.setAttribute("props", properties);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/properties.jsp");
		rd.forward(request, response);
		logProducer.registerLog(id,
				new StringBuilder().append(PREFIX).append(FINISH_IN_SERVLET)
						.append(this.getClass().getName()).append(GET).toString());
	}
}
