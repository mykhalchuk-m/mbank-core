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

import com.epam.mbank.entities.Client;
import com.epam.mbank.entities.Deposit;
import com.epam.mbank.exception.NoSuchItem;
import com.epam.mbank.loggin.services.LogProducer;
import com.epam.mbank.services.DepositService;

@WebServlet("/pre-open")
public class PreOpenDepositServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB(mappedName = "DepositService/local")
	private DepositService depositService;
	@EJB(mappedName = "LogProducer/local")
	private LogProducer logProducer;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		Client client = (Client) request.getSession().getAttribute(CLIENT);
		logProducer.registerLog(client.getId(), new StringBuilder().append(PREFIX).append(START_IN_SERVLET)
				.append(this.getClass().getName()).append(GET).toString());
		try {
			Long depositId = Long.parseLong(request.getParameter(ID));
			Deposit deposit = depositService.getDeposit(depositId);
			depositService.preOpenDeposit(client.getAccount(), deposit, false);
			response.sendRedirect("/mbank-client/deposits");
		} catch (NoSuchItem e) {
			request.setAttribute(FATAL_ERROR, e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("/deposits.jsp");
			rd.forward(request, response);
		} catch (NumberFormatException e) {
			request.setAttribute(FATAL_ERROR, "Unexpected exception.");
			RequestDispatcher rd = request.getRequestDispatcher("/deposits.jsp");
			rd.forward(request, response);
		}
		logProducer.registerLog(client.getId(), new StringBuilder().append(PREFIX).append(FINISH_IN_SERVLET)
				.append(this.getClass().getName()).append(GET).toString());
	}
}
