package com.epam.mbank.client.servlets;

import static com.epam.mbank.client.utils.LogMessages.*;
import static com.epam.mbank.client.utils.HttpVariable.*;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.mbank.client.services.PaginationList;
import com.epam.mbank.client.utils.WebPaginationBuilder;
import com.epam.mbank.entities.Client;
import com.epam.mbank.entities.Deposit;
import com.epam.mbank.exception.NoSuchItem;
import com.epam.mbank.loggin.services.LogProducer;

/**
 * Servlet implementation class DepositTableServlet
 */
@WebServlet(name = "deposits-table", urlPatterns = { "/deposits-table" })
public class DepositTableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB(mappedName = "client/PaginationDepositsList/local")
	private PaginationList<Deposit> depositsList;

	@EJB(mappedName = "LogProducer/local")
	private LogProducer logProducer;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		Client client = (Client) request.getSession().getAttribute(CLIENT);
		logProducer.registerLog(client.getId(), new StringBuilder().append(PREFIX).append(START_IN_SERVLET)
				.append(this.getClass().getName()).append(GET).toString());
		List<Deposit> deposits = null;
		int page = 1;
		try {
			page = Integer.parseInt(request.getParameter(PAGE));
		} catch (NumberFormatException e) {
		}
		try {
			request.setAttribute(ID, client.getId());
			deposits = depositsList.getItemsPerPage(client.getId(), page);
			request.setAttribute(PAGINATION, WebPaginationBuilder.getPagination(page,
					depositsList.getItemsCoutn(client.getId()), depositsList.getItemsCountPerPage()));
		} catch (NoSuchItem e) {
			request.getSession().setAttribute(CLIENT, null);
		}
		System.out.println(deposits.size());
		request.setAttribute(DEPOSITES, deposits);
		request.setAttribute(CURRENT_PAGE, page);
		request.setAttribute("asuncTableLoad", true);
		request.setAttribute("subTitle", "Clients Deposits:");
		RequestDispatcher rd = request.getRequestDispatcher("/deposits-table.jsp");
		rd.forward(request, response);
		logProducer.registerLog(client.getId(), new StringBuilder().append(PREFIX).append(FINISH_IN_SERVLET)
				.append(this.getClass().getName()).append(GET).toString());
	}

}
