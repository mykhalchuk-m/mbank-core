package com.epam.mbank.dao.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.epam.mbank.dao.GenericDAO;
import com.epam.mbank.exception.NeverRemovedException;
import com.epam.mbank.exception.NoSuchItem;

public abstract class AbstractGenericDAOImpl<T, K> implements GenericDAO<T, K> {
	private final static String LOG_MESSAGES_FILE = "logMessages.properties";
	protected Properties properties;
	protected Logger logger;

	public AbstractGenericDAOImpl() {
		properties = new Properties();
		try {
			InputStream in = getClass().getClassLoader().getResourceAsStream(LOG_MESSAGES_FILE);
			properties.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public T getById(K id) throws NoSuchItem {
		T t = null;
		try {
			Query query = getEntityManager().createNamedQuery(getEntityName() + ".getById");
			query.setParameter("id", id);
			t = (T) query.getSingleResult();
		} catch (NoResultException e) {
			throw new NoSuchItem(MessageFormat.format(properties.getProperty("err.account.nosuchitem"), id));
		}
		return t;
	}

	public int getItemCount() {
		Query query = getEntityManager().createNamedQuery(getEntityName() + ".getItemCount");
		int count = ((Long) query.getSingleResult()).intValue();
		return count;
	}

	public void save(T t) {
		getEntityManager().persist(t);
	}

	public void remove(T t) {
		throw new NeverRemovedException();
	}

	public T update(T t) {
		return getEntityManager().merge(t);
	}

	@SuppressWarnings("unchecked")
	protected List<T> findAllForPage(Query query, int pageNumber, int countPerPage) {
		query.setFirstResult((pageNumber - 1) * countPerPage);
		query.setMaxResults(countPerPage);
		return query.getResultList();
	}

	public List<T> getAll(int pageNumber, int countPerPage) {
		Query query = getEntityManager().createNamedQuery(getEntityName() + ".getAll");
		return findAllForPage(query, pageNumber, countPerPage);
	}

	protected abstract EntityManager getEntityManager();

	protected abstract String getEntityName();

}
