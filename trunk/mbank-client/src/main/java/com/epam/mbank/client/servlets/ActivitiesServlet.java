package com.epam.mbank.client.servlets;

import static com.epam.mbank.client.utils.HttpVariable.ACTIVITIES;
import static com.epam.mbank.client.utils.HttpVariable.AMOUNT;
import static com.epam.mbank.client.utils.HttpVariable.CLIENT;
import static com.epam.mbank.client.utils.HttpVariable.CURRENT_PAGE;
import static com.epam.mbank.client.utils.HttpVariable.ERROR;
import static com.epam.mbank.client.utils.HttpVariable.ERROR_PROPERY_FILE;
import static com.epam.mbank.client.utils.HttpVariable.ID;
import static com.epam.mbank.client.utils.HttpVariable.PAGE;
import static com.epam.mbank.client.utils.HttpVariable.PAGINATION;
import static com.epam.mbank.client.utils.LogMessages.FINISH_IN_SERVLET;
import static com.epam.mbank.client.utils.LogMessages.GET;
import static com.epam.mbank.client.utils.LogMessages.POST;
import static com.epam.mbank.client.utils.LogMessages.PREFIX;
import static com.epam.mbank.client.utils.LogMessages.START_IN_SERVLET;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.List;
import java.util.Properties;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.mbank.client.services.PaginationList;
import com.epam.mbank.client.utils.WebPaginationBuilder;
import com.epam.mbank.entities.Activity;
import com.epam.mbank.entities.Client;
import com.epam.mbank.entities.PropertyKeys;
import com.epam.mbank.exception.NoSuchItem;
import com.epam.mbank.exception.OperationNotAllowedException;
import com.epam.mbank.loggin.services.LogProducer;
import com.epam.mbank.services.ClientService;
import com.epam.mbank.services.MoneyTransactionService;
import com.epam.mbank.services.PropertiesService;

@WebServlet(name = "activities", urlPatterns = { "/activities" })
public class ActivitiesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String MESS_EXC_NUMFORMAT = "mess.exc.numbFormat";

	@EJB(mappedName = "client/PaginationActivitiesList/local")
	private PaginationList<Activity> activitiesList;
	@EJB(mappedName = "ClientService/local")
	private ClientService clientService;
	@EJB(mappedName = "LogProducer/local")
	private LogProducer logProducer;
	@EJB(mappedName = "MoneyTransactionService/local")
	private MoneyTransactionService mtService;
	@EJB(mappedName = "PropertiesService/local")
	private PropertiesService propertiesService;
	Properties properties;

	@Override
	public void init() throws ServletException {
		properties = new Properties();
		try {
			InputStream in = getClass().getClassLoader().getResourceAsStream(ERROR_PROPERY_FILE);
			properties.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		Long id = clientService.getClient(((Principal) request.getUserPrincipal()).getName()).getId();
		logProducer.registerLog(id,
				new StringBuilder().append(PREFIX).append(START_IN_SERVLET).append(this.getClass().getName())
						.append(GET).toString());
		int page = 1;
		List<Activity> activities = null;
		try {
			page = Integer.parseInt(request.getParameter(PAGE));
		} catch (NumberFormatException e) {
		}
		try {
			request.setAttribute(ID, id);
			activities = activitiesList.getItemsPerPage(id, page);
			request.setAttribute(
					PAGINATION,
					WebPaginationBuilder.getPagination(page, activitiesList.getItemsCoutn(id),
							activitiesList.getItemsCountPerPage()));
			request.setAttribute("commission", propertiesService.viewSystemProperty(PropertyKeys.COMMISSION_RATE));
		} catch (NoSuchItem e) {
			request.getSession().setAttribute(CLIENT, null);
		}
		request.setAttribute(CURRENT_PAGE, page);
		request.setAttribute(ACTIVITIES, activities);
		RequestDispatcher rd = request.getRequestDispatcher("/activities.jsp");
		rd.forward(request, response);
		logProducer.registerLog(id,
				new StringBuilder().append(PREFIX).append(FINISH_IN_SERVLET)
						.append(this.getClass().getName()).append(GET).toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		Long id = clientService.getClient(((Principal) request.getUserPrincipal()).getName()).getId();
		logProducer.registerLog(id,
				new StringBuilder().append(PREFIX).append(START_IN_SERVLET).append(this.getClass().getName())
						.append(POST).toString());
		try {
			Client client = clientService.getClient(id);
			Double amount = Double.parseDouble(request.getParameter(AMOUNT));
			String action = request.getParameter("action");
			if (action.equals("withdraw")) {
				mtService.withdraw(client.getAccount(), amount);
			} else if (action.equals("deposit")) {
				mtService.deposit(client.getAccount(), amount);
			}
			request.getSession().setAttribute(CLIENT, client);
			response.sendRedirect("/mbank-client/activities");
		} catch (OperationNotAllowedException e) {
			request.setAttribute(ERROR, e.getMessage());
			doGet(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof NumberFormatException) {
				request.setAttribute(ERROR, properties.getProperty(MESS_EXC_NUMFORMAT));
				doGet(request, response);
			} else {
				request.getSession().setAttribute(CLIENT, null);
				response.sendRedirect("/mbank-client/activities");
				e.printStackTrace();
			}
		} finally {
			logProducer.registerLog(
					id,
					new StringBuilder().append(PREFIX).append(FINISH_IN_SERVLET)
							.append(this.getClass().getName()).append(POST).toString());
		}
	}
}
