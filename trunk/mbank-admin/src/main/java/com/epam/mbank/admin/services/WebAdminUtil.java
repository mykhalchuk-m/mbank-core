package com.epam.mbank.admin.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.mbank.admin.utils.WebPaginationBuilder;
import com.epam.mbank.entities.Account;
import com.epam.mbank.entities.Activity;
import com.epam.mbank.entities.Client;
import com.epam.mbank.entities.ClientType;
import com.epam.mbank.entities.Deposit;
import com.epam.mbank.exception.NoSuchItem;

@Stateless(name = "WebAdminUtil")
@Local(WebUtil.class)
public class WebAdminUtil implements WebUtil {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@EJB(mappedName = "PaginationDepositsList/local")
	private PaginationList<Deposit> depositsList;

	@EJB(mappedName = "PaginationActivitiesList/local")
	private PaginationList<Activity> activitiesList;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.epam.mbank.services.WebUtil#createClientWithAccount(javax.servlet
	 * .http.HttpServletRequest)
	 */
	public Client createClientWithAccount(HttpServletRequest request) {
		Client client = new Client();
		client.setName(request.getParameter("name"));
		client.setEmail(request.getParameter("email"));
		client.setPassword(request.getParameter("pass"));
		client.setPhone(request.getParameter("phone"));
		client.setAddress(request.getParameter("address"));
		client.setComment(request.getParameter("comment"));
		Account account = new Account();
		account.setClient(client);
		try {
			account.setBalance(Double.parseDouble(request.getParameter("balance")));
		} catch (NumberFormatException e) {
			account.setBalance(-1);
		}
		client.setAccount(account);
		return client;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.epam.mbank.services.WebUtil#createClientForEdidting(javax.servlet
	 * .http.HttpServletRequest)
	 */
	public Client createClientForEdidting(HttpServletRequest request) throws NoSuchItem {
		Client client = new Client();
		client.setName(request.getParameter("name"));
		client.setEmail(request.getParameter("email"));
		client.setPassword(request.getParameter("pass"));
		client.setPhone(request.getParameter("phone"));
		client.setAddress(request.getParameter("address"));
		client.setComment(request.getParameter("comment"));
		client.setClientType(ClientType.valueOf(request.getParameter("clientType")));
		try {
			client.setId(Long.parseLong(request.getParameter("id")));
		} catch (NumberFormatException e) {
			logger.info(".[createClientForEdidting] Can't parse client id");
			throw new NoSuchItem("Invalid id");
		}
		return client;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.epam.mbank.services.WebUtil#getClientDeposits(javax.servlet.http.
	 * HttpServletRequest)
	 */
	public void getClientDeposits(HttpServletRequest request) {
		List<Deposit> deposits = null;
		int page = 1;
		Long id = null;
		try {
			page = Integer.parseInt(request.getParameter("p"));
		} catch (NumberFormatException e) {
		}
		try {
			id = Long.parseLong(request.getParameter("id"));
			request.setAttribute("id", id);
			deposits = depositsList.getItemsPerPageForClient(id, page);
		} catch (NumberFormatException e) {
			request.setAttribute("id", null);
			deposits = depositsList.getItemsPerPage(page);
		} catch (NoSuchItem e) {
			request.setAttribute("id", id);
			deposits = depositsList.getItemsPerPage(page);
		}
		request.setAttribute("deposits", deposits);
		request.setAttribute("currentPage", page);
		request.setAttribute(
				"pagination",
				WebPaginationBuilder.getPagination(page, depositsList.getItemsCoutn(id),
						depositsList.getItemsCountPerPage()));
		logger.info(".[getClientDeposits] for client id: " + id);
	}

	public void getClientActivity(HttpServletRequest request) {
		int page = 1;
		List<Activity> activities = null;
		Long id = null;
		try {
			page = Integer.parseInt(request.getParameter("p"));
		} catch (NumberFormatException e) {
		}
		try {
			id = Long.parseLong(request.getParameter("id"));
			request.setAttribute("id", id);
			activities = activitiesList.getItemsPerPageForClient(id, page);
		} catch (NumberFormatException e) {
			request.setAttribute("id", null);
			activities = activitiesList.getItemsPerPage(page);
		} catch (NoSuchItem e) {
			request.setAttribute("id", null);
			activities = activitiesList.getItemsPerPage(page);
		}
		request.setAttribute(
				"pagination",
				WebPaginationBuilder.getPagination(page, activitiesList.getItemsCoutn(id),
						activitiesList.getItemsCountPerPage()));
		request.setAttribute("currentPage", page);
		request.setAttribute("activities", activities);
		logger.info("Recived all activities: " + activities.size());
	}

}
