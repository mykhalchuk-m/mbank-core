package com.epam.mbank.client.servlets;

import static com.epam.mbank.client.utils.LogMessages.*;
import static com.epam.mbank.client.utils.HttpVariable.*;

import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.mbank.client.services.PaginationList;
import com.epam.mbank.client.utils.WebPaginationBuilder;
import com.epam.mbank.entities.Client;
import com.epam.mbank.entities.Deposit;
import com.epam.mbank.exception.NoSuchItem;
import com.epam.mbank.loggin.services.LogProducer;
import com.epam.mbank.services.ClientService;
import com.epam.mbank.services.DepositService;

@WebServlet(name = "deposits", urlPatterns = { "/deposits" })
public class DepositServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@EJB(mappedName = "client/PaginationDepositsList/local")
	private PaginationList<Deposit> depositsList;
	@EJB(mappedName = "LogProducer/local")
	private LogProducer logProducer;
	@EJB(mappedName = "DepositService/local")
	private DepositService depositService;
	@EJB(mappedName = "ClientService/local")
	private ClientService clientService;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		Client client = clientService.getClient(((Principal) request.getUserPrincipal()).getName());
		logger.info(".[GET] Deposits for client: " + client.getName());
		logProducer.registerLog(client.getId(), new StringBuilder().append(PREFIX).append(START_IN_SERVLET)
				.append(this.getClass().getName()).append(GET).toString());
		List<Deposit> deposits = null;
		int page = 1;
		try {
			page = Integer.parseInt(request.getParameter(PAGE));
		} catch (NumberFormatException e) {
		}
		try {
			request.setAttribute("id", client.getId());
			logger.info(".[GET] page: " + page);
			deposits = depositsList.getItemsPerPage(client.getId(), page);
			request.setAttribute(PAGINATION, WebPaginationBuilder.getPagination(page,
					depositsList.getItemsCoutn(client.getId()), depositsList.getItemsCountPerPage()));
		} catch (NoSuchItem e) {
			logger.error(".[GET] " + e.getMessage());
			request.setAttribute(FATAL_ERROR, "Fatal ERROR!!!");
			request.getSession().setAttribute("client", null);
		}
		request.setAttribute(DEPOSITES, deposits);
		request.setAttribute(CURRENT_PAGE, page);
		logger.info(".[GET] Servlet finished");
		RequestDispatcher rd = request.getRequestDispatcher("/deposits.jsp");
		rd.forward(request, response);
		logProducer.registerLog(client.getId(), new StringBuilder().append(PREFIX).append(FINISH_IN_SERVLET)
				.append(this.getClass().getName()).append(GET).toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		List<String> errors = null;
		Client client = clientService.getClient(((Principal) request.getUserPrincipal()).getName());
		logProducer.registerLog(client.getId(), new StringBuilder().append(PREFIX).append(START_IN_SERVLET)
				.append(this.getClass().getName()).append(POST).toString());
		try {
			Long id = Long.parseLong(request.getParameter(ID));
			if (id != client.getId()) {
				request.getSession().setAttribute(CLIENT, null);
				response.sendRedirect("/mbank-client");
			}
			double amount = Double.parseDouble(request.getParameter(AMOUNT));
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			format.setLenient(false);
			Date date = format.parse(request.getParameter("closing"));
			depositService.createNewDeposite(client, amount, date);
			response.sendRedirect("/mbank-client/deposits");
		} catch (ParseException e) {
			errors = new ArrayList<String>();
			errors.add("Use pattern dd/MM/yyyy to input date");
			request.setAttribute(ERRORS, errors);
			doGet(request, response);
		} catch (Exception e) {
			if (e instanceof NumberFormatException) {
				errors = new ArrayList<String>();
				errors.add("Ivalid number");
				request.setAttribute(ERRORS, errors);
				doGet(request, response);
			} else if (e.getCause() instanceof ConstraintViolationException) {
				errors = new ArrayList<String>();
				Set<ConstraintViolation<?>> exceptions = ((ConstraintViolationException) e.getCause())
						.getConstraintViolations();
				for (ConstraintViolation<?> constraintViolation : exceptions) {
					errors.add(constraintViolation.getMessage());
				}
				request.setAttribute(ERRORS, errors);
				doGet(request, response);
			}
		} finally {
			logProducer.registerLog(
					client.getId(),
					new StringBuilder().append(PREFIX).append(FINISH_IN_SERVLET)
							.append(this.getClass().getName()).append(POST).toString());
		}
	}
}
