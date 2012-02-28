package com.epam.mbank.services;

import java.util.List;

import com.epam.mbank.entities.Account;
import com.epam.mbank.entities.Client;
import com.epam.mbank.exception.NoSuchItem;

public interface ClientService {

	/**
	 * Create new client and add it to data base
	 */
	void createClient(Client client);

	/**
	 * Remove existed in data base client if there is no such client then throw
	 * <code>NoSuchItem</code> exception
	 * 
	 * @throws NoSuchItem
	 */
	void removeClient(Client client) throws NoSuchItem;

	/**
	 * Crate new account for client. Done when adding new client – & only once
	 */
	void createAccount(Client client);

	/**
	 * Remove existed in data base account. Done when deleting new client – &
	 * only once
	 */
	void removeAccount(Account account);

	/**
	 * Updating client details.
	 */
	void updateClient(Client client) throws NoSuchItem;

	/**
	 * Return all clients for some page with given count of items per page
	 */
	List<Client> getClients(int pageNumber, int countPerPage);

	/**
	 * Return client with given id. Throw <code>NoSuchItem</code> if no client
	 * found
	 * 
	 * @throws NoSuchItem
	 */
	Client getClient(Long id) throws NoSuchItem;
	
	/**
	 * Return all accounts for some page with given count of items per page
	 */
	List<Account> getAccounts(int pageNumber, int countPerPage);
	
	/**
	 * Return count of all accounts in data base.
	 */
	int getCountOfAccounts();

	/**
	 * Return count of all clients in data base.
	 */
	int getCountOfClients();
	
	/**
	 * Receive client by his name
	 */
	Client getClient(String name);
}
