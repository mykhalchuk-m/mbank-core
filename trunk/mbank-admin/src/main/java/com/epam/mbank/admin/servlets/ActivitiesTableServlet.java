package com.epam.mbank.admin.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.mbank.admin.services.WebUtil;

/**
 * Servlet implementation class ActivitiesTableServlet
 */
@WebServlet(name = "activities-table", urlPatterns = { "/activities-table" })
public class ActivitiesTableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB(mappedName = "WebAdminUtil/local")
	private WebUtil webUtil;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		webUtil.getClientActivity(request);
		request.setAttribute("asuncTableLoad", true);
		request.setAttribute("subTitle", "Clients Activities:");
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/activities-table.jsp");
		rd.forward(request, response);
	}
}
