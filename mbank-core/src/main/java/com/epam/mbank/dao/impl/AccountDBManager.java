package com.epam.mbank.dao.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.epam.mbank.dao.AccountManager;
import com.epam.mbank.entities.Account;

@Stateless
public class AccountDBManager extends AbstractGenericDAOImpl<Account, Long> implements AccountManager {

	@PersistenceContext(name = "mbank")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	protected String getEntityName() {
		return Account.class.getSimpleName();
	}
}
