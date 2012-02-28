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
 * Servlet implementation class CreateDeposit
 */
@WebServlet(name = "deposits", urlPatterns = { "/deposits" })
public class DepositServlet extends HttpServlet {
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
		RequestDispatcher rd = request.getRequestDispatcher("/deposits.jsp");
		rd.forward(request, response);
	}
}
