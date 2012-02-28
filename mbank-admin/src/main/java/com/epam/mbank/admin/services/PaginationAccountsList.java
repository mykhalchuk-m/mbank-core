package com.epam.mbank.admin.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import com.epam.mbank.entities.Account;
import com.epam.mbank.exception.NoSuchItem;
import com.epam.mbank.services.ClientService;

@Stateless(name = "PaginationAccountsList")
@Local(PaginationList.class)
public class PaginationAccountsList implements PaginationList<Account> {
	@EJB(mappedName = "ClientService/local")
	private ClientService clientService;

	public int getItemsCountPerPage() {
		return ELEMENTS_PER_PAGE;
	}

	public int getItemsCoutn(Long id) {
		return clientService.getCountOfAccounts(); 
	}

	public List<Account> getItemsPerPageForClient(Long id, int page)
			throws NoSuchItem {
		return getItemsPerPage(page);
	}
	
	public List<Account> getItemsPerPage(int page) {
		return clientService.getAccounts(page, ELEMENTS_PER_PAGE);
	}
}
