package com.epam.mbank.admin.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import com.epam.mbank.entities.Client;
import com.epam.mbank.entities.Deposit;
import com.epam.mbank.exception.NoSuchItem;
import com.epam.mbank.services.ClientService;
import com.epam.mbank.services.DepositService;

@Stateless(name = "PaginationDepositsList")
@Local(PaginationList.class)
public class PaginationDepositsList implements PaginationList<Deposit> {
	@EJB(mappedName = "ClientService/local")
	private ClientService clientService;
	@EJB(mappedName = "DepositService/local")
	private DepositService depositService;

	public List<Deposit> getItemsPerPage(int page) {
		return depositService.getDeposits(page, ELEMENTS_PER_PAGE);
	}

	public int getItemsCountPerPage() {
		return ELEMENTS_PER_PAGE;
	}

	public int getItemsCoutn(Long id) {
		try {
			return id == null ? depositService.getCountOfDeposits() : depositService
					.getCountOfDeposits(clientService.getClient(id));
		} catch (NoSuchItem e) {
			return depositService.getCountOfDeposits();
		}
	}

	public List<Deposit> getItemsPerPageForClient(Long id, int page) throws NoSuchItem {
		Client client = clientService.getClient(id);
		return depositService.getDeposites(client, page, ELEMENTS_PER_PAGE);
	}
}
