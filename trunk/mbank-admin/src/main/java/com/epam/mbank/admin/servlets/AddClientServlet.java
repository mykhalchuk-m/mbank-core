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
import com.epam.mbank.entities.Client;
import com.epam.mbank.entities.validation.CreateClientGroup;
import com.epam.mbank.services.ClientService;

@WebServlet(name = "add-client", urlPatterns = { "/add-client" })
public class AddClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@EJB
	private WebUtil clientServise;

	@EJB(mappedName = "ClientService/local")
	private ClientService clientService;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/addclient.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info("Strated create client in servlet");
		Client client;
		client = clientServise.createClientWithAccount(request);
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
			clientService.createClient(client);
			logger.info("Create client in servlet finished");
			response.sendRedirect("/mbank-admin/clients");
		}
	}
}
