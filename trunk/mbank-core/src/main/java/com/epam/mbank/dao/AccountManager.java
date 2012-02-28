package com.epam.mbank.dao;

import java.util.List;

import javax.ejb.Local;

import com.epam.mbank.entities.Account;

@Local
public interface AccountManager extends GenericDAO<Account, Long> {
	List<Account> getAll(int pageNumber, int countPerPage);
}
