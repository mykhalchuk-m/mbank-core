package com.epam.mbank.admin.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.mbank.admin.utils.SettingsKeys;
import com.epam.mbank.dao.PropertiesManager;
import com.epam.mbank.entities.Property;

/**
 * Servlet implementation class SettingsServlet
 */
@WebServlet("/props")
public class SettingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB(mappedName = "PropertiesManager/local")
	private PropertiesManager propertiesManager;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<Property> properties = propertiesManager.getAll();
		SettingsKeys keys = new SettingsKeys();
		Map<String, Property> map =  keys.getWebKeys(properties);
		request.setAttribute("props", map);
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/properties.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		SettingsKeys keys = new SettingsKeys();
		Set<String> propKeys = keys.getPropertyKeys();
		for (String propKey : propKeys) {
			Property property = new Property();
			property.setName(propKey);
			property.setValue(request.getParameter(propKey));
			propertiesManager.update(property);
		}
		doGet(request, response);
	}
}
