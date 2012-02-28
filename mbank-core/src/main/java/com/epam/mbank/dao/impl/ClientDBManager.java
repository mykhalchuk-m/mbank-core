package com.epam.mbank.dao.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.epam.mbank.dao.ClientManager;
import com.epam.mbank.entities.Client;

@Stateless
public class ClientDBManager extends AbstractGenericDAOImpl<Client, Long> implements ClientManager {

	@PersistenceContext(name = "mbank")
	private EntityManager em;

	public Client getByName(String name) {
		Query query = em.createNamedQuery("Client.getByNamePass");
		query.setParameter("name", name);
		Client client = null;
		try {
			client = (Client) query.getSingleResult();
		} catch (NoResultException e) {
			client = null;
		}
		return client;
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	protected String getEntityName() {
		return Client.class.getSimpleName();
	}
	
	public void remove(Client client) {
		client.setDeleted(true);
		update(client);
	}

}
