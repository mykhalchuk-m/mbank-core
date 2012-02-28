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
import com.epam.mbank.entities.Activity;
import com.epam.mbank.entities.Client;
import com.epam.mbank.exception.NoSuchItem;
import com.epam.mbank.loggin.services.LogProducer;

/**
 * Servlet implementation class ActivitiesTableServlet
 */
@WebServlet(name = "activities-table", urlPatterns = { "/activities-table" })
public class ActivitiesTableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB(mappedName = "client/PaginationActivitiesList/local")
	private PaginationList<Activity> activitiesList;

	@EJB(mappedName = "LogProducer/local")
	private LogProducer logProducer;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		Long id = ((Client) request.getSession().getAttribute(CLIENT)).getId();
		logProducer.registerLog(id,
				new StringBuilder().append(PREFIX).append(START_IN_SERVLET).append(this.getClass().getName())
						.append(GET).toString());
		int page = 1;
		List<Activity> activities = null;
		try {
			page = Integer.parseInt(request.getParameter("p"));
		} catch (NumberFormatException e) {
		}
		try {
			request.setAttribute(ID, id);
			activities = activitiesList.getItemsPerPage(id, page);
			request.setAttribute(
					PAGINATION,
					WebPaginationBuilder.getPagination(page, activitiesList.getItemsCoutn(id),
							activitiesList.getItemsCountPerPage()));
		} catch (NoSuchItem e) {
			request.getSession().setAttribute("client", null);
		}
		request.setAttribute(CURRENT_PAGE, page);
		request.setAttribute(ACTIVITIES, activities);
		request.setAttribute("asuncTableLoad", true);
		request.setAttribute("subTitle", "Clients Activities:");
		System.out.println("in activities table servlet");
		RequestDispatcher rd = request.getRequestDispatcher("/activities-table.jsp");
		rd.forward(request, response);
		logProducer.registerLog(id,
				new StringBuilder().append(PREFIX).append(FINISH_IN_SERVLET)
						.append(this.getClass().getName()).append(GET).toString());
	}
}
