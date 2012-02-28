package com.epam.mbank.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.mbank.dao.DepositManager;
import com.epam.mbank.entities.Client;
import com.epam.mbank.entities.Deposit;

@Stateless
public class DepositDBManager extends AbstractGenericDAOImpl<Deposit, Long> implements DepositManager {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@PersistenceContext(name = "mbank")
	private EntityManager em;

	public List<Deposit> getAllForClient(Client client, int page, int countPerPage) {
		Query query = em.createNamedQuery("Deposit.getAllForClient");
		query.setParameter("id", client.getId());
		List<Deposit> deposits = findAllForPage(query, page, countPerPage);
		return deposits;
	}

	public int getItemCountForClient(Client client) {
		Query query = em.createNamedQuery("Deposit.getItemCountForClient");
		query.setParameter("id", client.getId());
		int count = ((Long) query.getSingleResult()).intValue();
		logger.info(MessageFormat.format(properties.getProperty("log.info.depositcount.client"),
				client.getId(), count));
		return count;
	}

	public void remove(Deposit deposit) {
		// removing detached object
		em.remove(em.merge(deposit));
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	protected String getEntityName() {
		return Deposit.class.getSimpleName();
	}
}
