package com.epam.mbank.dao.impl;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.epam.mbank.dao.PropertiesManager;
import com.epam.mbank.entities.Client;
import com.epam.mbank.entities.ClientType;
import com.epam.mbank.entities.PropertyKeys;
import com.epam.mbank.entities.Property;

@Stateless(name = "PropertiesManager")
@Local(PropertiesManager.class)
public class PropertiesDBManager implements PropertiesManager {
	@PersistenceContext(name = "mbank")
	private EntityManager em;

	public Property getById(String id) {
		return em.find(Property.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Property> getAll() {
		return em.createQuery("from Property").getResultList();
	}

	public void save(Property t) {
		em.persist(t);

	}

	public void remove(Property t) {
		em.remove(t);

	}

	public Property update(Property t) {
		return em.merge(t);
	}

	public double getCreditLimit(ClientType clientType) {
		double creditLimit = 0;
		if (clientType.equals(ClientType.REGULAR)) {
			creditLimit = parseDoubleValue(PropertyKeys.REGULAR_CREDIT_LIMIT);
		} else if (clientType.equals(ClientType.GOLD)) {
			creditLimit = parseDoubleValue(PropertyKeys.GOLD_CREDIT_LIMIT);
		} else if (clientType.equals(ClientType.PLATINUM)) {
			creditLimit = parseDoubleValue(PropertyKeys.PLATINUM_CREDIT_LIMIT);
		}
		return creditLimit;
	}

	public double getDepositCommission(ClientType clientType) {
		if (clientType.equals(ClientType.REGULAR)) {
			return parseDoubleValue(PropertyKeys.REGULAR_DEPOSIT_COMMISSION);
		} else if (clientType.equals(ClientType.GOLD)) {
			return parseDoubleValue(PropertyKeys.GOLD_DEPOSIT_COMMISSION);
		} else if (clientType.equals(ClientType.PLATINUM)) {
			return parseDoubleValue(PropertyKeys.PLATINUM_DEPOSIT_COMMISSION);
		}
		return 0;
	}

	public double getDepositInterest(ClientType clientType) {
		if (clientType.equals(ClientType.REGULAR)) {
			return parseDoubleValue(PropertyKeys.REGULAR_DAILY_INTEREST);
		} else if (clientType.equals(ClientType.GOLD)) {
			return parseDoubleValue(PropertyKeys.GOLD_DAILY_INTEREST);
		} else if (clientType.equals(ClientType.PLATINUM)) {
			return parseDoubleValue(PropertyKeys.PLATINUM_DAILY_INTEREST);
		}
		return 0;
	}

	public ClientType getClientType(double amount) {
		if (amount < parseDoubleValue(PropertyKeys.REGULAR_DEPOSIT_RATE)) {
			return ClientType.REGULAR;
		} else if (amount < parseDoubleValue(PropertyKeys.GOLD_DEPOSIT_RATE)) {
			return ClientType.GOLD;
		} else {
			return ClientType.PLATINUM;
		}
	}

	public void checkClientType(Client client) {
		ClientType oldClientType = client.getClientType();
		if (oldClientType == null)
			oldClientType = ClientType.REGULAR;
		ClientType newClientType = getClientType(client.getAccount()
				.getBalance());
		if (!oldClientType.equals(newClientType)) {
			client.setClientType(newClientType);
			client.getAccount().setCreditLimit(getCreditLimit(newClientType));
		}
	}

	private double parseDoubleValue(String arg) {
		return Double.parseDouble(getById(arg).getValue());
	}

	public List<Property> findAllForPage(int pageNumber, int countPerPage) {
		return getAll();
	}
	
	public int getItemCount() {
		Query query = em
				.createQuery("select count(a) from Property a");
		int count = (Integer) query.getSingleResult();
		return count;
	}
}
