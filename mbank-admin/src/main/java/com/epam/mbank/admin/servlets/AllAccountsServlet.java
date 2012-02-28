package com.epam.mbank.admin.servlets;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.mbank.admin.services.PaginationList;
import com.epam.mbank.admin.utils.WebPaginationBuilder;
import com.epam.mbank.entities.Account;

/**
 * Servlet implementation class GetAllAccounts
 */
@WebServlet("/accounts")
public class AllAccountsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB(mappedName = "PaginationAccountsList/local")
	private PaginationList<Account> accountsList;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		int page = 1;
		try {
			page = Integer.parseInt(request.getParameter("p"));
		} catch (NumberFormatException e) {
		}
		List<Account> accounts = accountsList.getItemsPerPage(page);
		request.setAttribute("accounts", accounts);
		request.setAttribute("currentPage", page);
		List<String> pagination;
		pagination = WebPaginationBuilder.getPagination(page, accountsList.getItemsCoutn(null),
				accountsList.getItemsCountPerPage());
		request.setAttribute("pagination", pagination);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/accounts.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		doGet(request, response);
	}

}
