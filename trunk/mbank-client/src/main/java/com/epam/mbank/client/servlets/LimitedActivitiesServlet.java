package com.epam.mbank.client.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.mbank.client.utils.BuildJson;
import com.epam.mbank.entities.Activity;
import com.epam.mbank.entities.Client;
import com.epam.mbank.exception.NoSuchItem;
import com.epam.mbank.services.ActivityService;
import com.epam.mbank.services.ClientService;

/**
 * Servlet implementation class LinitedActivities
 */
@WebServlet(name = "limit-acts", urlPatterns = { "/limit-acts" })
public class LimitedActivitiesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB(mappedName = "ClientService/local")
	private ClientService clientService;
	@EJB(mappedName = "ActivityService/local")
	private ActivityService activityService;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		Client client = clientService.getClient(((Principal) request.getUserPrincipal()).getName());
		List<Activity> activities = null;
		PrintWriter out = response.getWriter();
		try {
			activities = activityService.getLastLimActs(client, 5);
		} catch (NoSuchItem e) {
			out.write("{description: 'server error...'}");
			return;
		}
		out.write(BuildJson.buildJsonFromActivity(activities));
	}
}
