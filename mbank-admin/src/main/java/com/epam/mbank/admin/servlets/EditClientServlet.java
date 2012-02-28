package com.epam.mbank.admin.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.mbank.admin.services.WebUtil;
import com.epam.mbank.admin.utils.BuildJson;
import com.epam.mbank.entities.Client;
import com.epam.mbank.entities.validation.CreateClientGroup;
import com.epam.mbank.exception.NoSuchItem;
import com.epam.mbank.services.ClientService;

/**
 * Servlet implementation class EditClientServlet
 */
@WebServlet(name = "edit", urlPatterns = { "/edit" })
public class EditClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@EJB(mappedName = "ClientService/local")
	private ClientService clientService;

	@EJB
	private WebUtil webUtil;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info(".[GET] Get client details");
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			Client client = clientService.getClient(id);
			String json = request.getParameter("json");
			if (json != null) {
				response.getWriter().write(BuildJson.buildJsonFromClient(client));
				return;
			}
			request.setAttribute("id", id);
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/details.jsp");
			rd.forward(request, response);
		} catch (NumberFormatException nfe) {
			logger.error(nfe.getMessage());
			response.sendRedirect("/mbank-admin/clients");
		} catch (NoSuchItem e) {
			logger.error(e.getMessage());
			request.setAttribute("errorMessage", e.getMessage());
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/all-clients");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info(".[POST] Start editing client");
		Client client = null;
		try {
			client = webUtil.createClientForEdidting(request);
		} catch (NoSuchItem e1) {
			logger.error(e1.getMessage());
			request.setAttribute("errorMessage", e1.getMessage());
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/all-clients");
			rd.forward(request, response);
		}
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Client>> violations = validator.validate(
				client, CreateClientGroup.class);
		List<String> errors = new ArrayList<String>();
		for (ConstraintViolation<Client> constraintViolation : violations) {
			logger.error(constraintViolation.getMessage() + "  "
					+ constraintViolation.getPropertyPath());
			errors.add(constraintViolation.getMessage());
		}
		if (errors.size() > 0) {
			request.setAttribute("errors", errors);
			doGet(request, response);
		} else {
			try {
				
				clientService.updateClient(client);
				logger.info(".[POST] Updating client finished");
				doGet(request, response);
			} catch (NoSuchItem e) {
				logger.info(".[POST] " + e.getMessage());
				request.setAttribute("errorMessage", e.getMessage());
				RequestDispatcher rd = getServletContext().getRequestDispatcher(
						"/all-clients");
				rd.forward(request, response);
			}
		}
	}

}
