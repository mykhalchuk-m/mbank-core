package com.epam.mbank.admin.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.mbank.admin.services.WebUtil;


/**
 * Servlet implementation class GetAllActivities
 */
@WebServlet(name = "activities", urlPatterns = { "/activities" })
public class ActivitiesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@EJB(mappedName = "WebAdminUtil/local")
	private WebUtil webUtil;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info(".[GET] activities servlet");
		webUtil.getClientActivity(request);
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/activities.jsp");
		rd.forward(request, response);
	}
}
