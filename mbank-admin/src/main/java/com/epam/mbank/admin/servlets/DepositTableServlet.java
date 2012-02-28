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
 * Servlet implementation class DepositTableServlet
 */
@WebServlet(name = "deposits-table", urlPatterns = { "/deposits-table" })
public class DepositTableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB(mappedName = "WebAdminUtil/local")
	private WebUtil clientService;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		clientService.getClientDeposits(request);
		request.setAttribute("asuncTableLoad", true);
		request.setAttribute("subTitle", "Clients Deposits:");
		RequestDispatcher rd = request
				.getRequestDispatcher("/deposits-table.jsp");
		rd.forward(request, response);
	}

}
