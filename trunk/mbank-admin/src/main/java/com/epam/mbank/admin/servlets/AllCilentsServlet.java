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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.mbank.admin.services.PaginationList;
import com.epam.mbank.admin.utils.WebPaginationBuilder;
import com.epam.mbank.entities.Client;

/**
 * Servlet implementation class GetAllClent
 */
@WebServlet(name = "clients", urlPatterns = { "/clients" })
public class AllCilentsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@EJB(mappedName = "PaginationClientList/local")
	private PaginationList<Client> pagintationList;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		logger.info(".[GET] Started servlet all-clients");
		int page = 1;
		try {
			page = Integer.parseInt(request.getParameter("p"));
		} catch (NumberFormatException e) {
		}
		List<Client> clients = pagintationList.getItemsPerPage(page);
		logger.info(".[GET] Clients retieved");
		List<String> pagination = WebPaginationBuilder.getPagination(page,
				pagintationList.getItemsCoutn(null), pagintationList.getItemsCountPerPage());
		request.setAttribute("pagination", pagination);
		request.setAttribute("clients", clients);
		request.setAttribute("currentPage", page);

		RequestDispatcher rd = getServletContext().getRequestDispatcher("/allclients.jsp");
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
