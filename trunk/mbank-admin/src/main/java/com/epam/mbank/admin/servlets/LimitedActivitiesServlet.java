package com.epam.mbank.admin.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.mbank.admin.utils.BuildJson;
import com.epam.mbank.entities.Activity;
import com.epam.mbank.services.ActivityService;

/**
 * Servlet implementation class LinitedActivities
 */
@WebServlet(name = "limit-acts", urlPatterns = { "/limit-acts" })
public class LimitedActivitiesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB(mappedName = "ActivityService/local")
	private ActivityService activityService;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<Activity> activities = activityService.getLastLimActs(5);
		PrintWriter out = response.getWriter();
		out.write(BuildJson.buildJsonFromActivity(activities));
	}
}

