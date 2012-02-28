package com.epam.mbank.client.servlets;

import static com.epam.mbank.client.utils.LogMessages.*;
import static com.epam.mbank.client.utils.HttpVariable.*;

import java.io.IOException;
import java.security.Principal;
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

import com.epam.mbank.client.utils.BuildJson;
import com.epam.mbank.entities.Account;
import com.epam.mbank.entities.Client;
import com.epam.mbank.entities.ClientType;
import com.epam.mbank.entities.validation.CreateClientGroup;
import com.epam.mbank.exception.NoSuchItem;
import com.epam.mbank.loggin.services.LogProducer;
import com.epam.mbank.services.ClientService;

@WebServlet(name = "details", urlPatterns = { "/details" })
public class DetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB(mappedName = "ClientService/local")
	private ClientService clientService;

	@EJB(mappedName = "LogProducer/local")
	private LogProducer logProducer;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		Client client = clientService.getClient(((Principal) request.getUserPrincipal()).getName());
		logProducer.registerLog(client.getId(), new StringBuilder().append(PREFIX).append(START_IN_SERVLET)
				.append(this.getClass().getName()).append(GET).toString());
		String json = request.getParameter("json");
		if (json != null) {
			response.getWriter().write(BuildJson.buildJsonFromClient(client));
			return;
		}
		request.setAttribute("client", client);
		RequestDispatcher rd = request.getRequestDispatcher("/details.jsp");
		rd.forward(request, response);
		logProducer.registerLog(client.getId(), new StringBuilder().append(PREFIX).append(FINISH_IN_SERVLET)
				.append(this.getClass().getName()).append(GET).toString());
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		Long id = (clientService.getClient(((Principal) request.getUserPrincipal()).getName())).getId();
		logProducer.registerLog(id,
				new StringBuilder().append(PREFIX).append(START_IN_SERVLET).append(this.getClass().getName())
						.append(POST).toString());
		List<String> errors = new ArrayList<String>();
		Client client = new Client();
		try {
			client.setName(request.getParameter("name"));
			client.setEmail(request.getParameter("email"));
			client.setPassword(request.getParameter("pass"));
			client.setPhone(request.getParameter("phone"));
			client.setAddress(request.getParameter("address"));
			client.setComment(request.getParameter("comment"));
			client.setClientType(ClientType.valueOf(request.getParameter("clientType")));
			try {
				client.setId(Long.parseLong(request.getParameter(ID)));
			} catch (NumberFormatException e) {
				throw new NoSuchItem("Invalid id");
			}
			if (id != client.getId()) {
				throw new NoSuchItem("Incompatible id with logged user id");
			}
		} catch (NoSuchItem e) {
			errors.add(e.getMessage());
			request.setAttribute(ERRORS, errors);
			doGet(request, response);
		}
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Client>> violations = validator.validate(client, CreateClientGroup.class);
		for (ConstraintViolation<Client> constraintViolation : violations) {
			errors.add(constraintViolation.getMessage());
		}
		if (errors.size() > 0) {
			request.setAttribute(ERRORS, errors);
			doGet(request, response);
		} else {
			try {
				clientService.updateClient(client);
				Account account = ((Client) request.getSession().getAttribute(CLIENT)).getAccount();
				client.setAccount(account);
				request.getSession().setAttribute(CLIENT, client);
				doGet(request, response);
			} catch (NoSuchItem e) {
				errors.add(e.getMessage());
				request.setAttribute(ERRORS, errors);
				doGet(request, response);
			}
		}
	}
}
