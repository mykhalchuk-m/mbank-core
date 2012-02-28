package com.epam.mbank.admin.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import com.epam.mbank.entities.Client;
import com.epam.mbank.exception.NoSuchItem;
import com.epam.mbank.services.ClientService;

@Stateless(name = "PaginationClientList")
@Local(PaginationList.class)
public class PaginationClientList implements PaginationList<Client> {
	@EJB(mappedName = "ClientService/local")
	private ClientService clientService;

	public int getItemsCountPerPage() {
		return ELEMENTS_PER_PAGE;
	}

	public int getItemsCoutn(Long id) {
		return clientService.getCountOfClients();
	}

	public List<Client> getItemsPerPageForClient(Long id, int page)
			throws NoSuchItem {
		return getItemsPerPage(page);
	}

	public List<Client> getItemsPerPage(int page) {
		return clientService.getClients(page, ELEMENTS_PER_PAGE);
	}
}
