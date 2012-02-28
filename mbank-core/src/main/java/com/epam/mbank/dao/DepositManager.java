package com.epam.mbank.dao;

import java.util.List;

import javax.ejb.Local;

import com.epam.mbank.entities.Client;
import com.epam.mbank.entities.Deposit;

@Local
public interface DepositManager extends GenericDAO<Deposit, Long> {

	List<Deposit> getAllForClient(Client client, int page, int countPerPage);

	List<Deposit> getAll(int page, int countPerPage);
	
	int getItemCountForClient(Client client);
}
