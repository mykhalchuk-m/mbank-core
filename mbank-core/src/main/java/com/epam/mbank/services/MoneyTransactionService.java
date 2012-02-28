package com.epam.mbank.services;

import com.epam.mbank.entities.Account;
import com.epam.mbank.exception.NoSuchItem;
import com.epam.mbank.exception.OperationNotAllowedException;

public interface MoneyTransactionService {
	/**
	 * Decreasing account balance. If <code>amount</code> biggest than both
	 * account credit limit and account balance then throw
	 * <code>OperationNotAllowedException</code>, if account not existed in data
	 * base then throw <code>NoSuchItem</code>.
	 * 
	 * @throws OperationNotAllowedException
	 *             , NoSuchItem
	 */
	void withdraw(Account account, double amount) throws OperationNotAllowedException, NoSuchItem;

	/**
	 * Increasing account balance. if account not existed in data base then
	 * throw <code>NoSuchItem</code>.
	 * 
	 * @throws OperationNotAllowedException
	 *             , NoSuchItem
	 */
	void deposit(Account account, double amount) throws OperationNotAllowedException, NoSuchItem;

	/**
	 * Increase bank balance by bill amount
	 */
	public void incBankBalance(double amount);
}
