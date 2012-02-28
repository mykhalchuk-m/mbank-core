package com.epam.mbank.services;

import java.util.Date;
import java.util.List;

import com.epam.mbank.entities.Account;
import com.epam.mbank.entities.Client;
import com.epam.mbank.entities.Deposit;
import com.epam.mbank.exception.NoSuchItem;

public interface DepositService {
	/**
	 * Create new deposit
	 */
	void createNewDeposite(Client client, double amount, Date closetDate) throws NoSuchItem;

	/**
	 * Money is transferred after charging a commission
	 */
	void preOpenDeposit(Account account, Deposit deposit, boolean all) throws NoSuchItem;

	/**
	 * Close given deposits
	 */
	void closeDeposit(Deposit deposit);
	
	/**
	 * Return deposit by id. If There is no such deposit throw <code>NoSuchItem</code>
	 * 
	 * @throws NoSuchItem
	 */
	Deposit getDeposit(Long id) throws NoSuchItem;

	/**
	 * Return all deposits for some page with given count of items per page
	 */
	List<Deposit> getDeposits(int pageNumber, int countPerPage);

	/**
	 * Return all deposits for some page with given count of items per page for
	 * one client. If client not exist in data base then throws
	 * <code>NoSuchItem</code>
	 * 
	 * @throws NoSuchItem
	 */
	List<Deposit> getDeposites(Client client, int pageNumber, int countPerPage) throws NoSuchItem;
	
	/**
	 * Return count of all deposits in data base.
	 */
	int getCountOfDeposits();

	/**
	 * Get count of all deposits for followed client
	 */
	int getCountOfDeposits(Client client);

}
