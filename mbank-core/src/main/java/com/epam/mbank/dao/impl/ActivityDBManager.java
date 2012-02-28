package com.epam.mbank.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.mbank.dao.ActivityManager;
import com.epam.mbank.entities.Activity;
import com.epam.mbank.entities.Client;
import com.epam.mbank.exception.NoSuchItem;

@Stateless
public class ActivityDBManager extends AbstractGenericDAOImpl<Activity, Long> implements ActivityManager {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@PersistenceContext(name = "mbank")
	private EntityManager em;

	@PostConstruct
	void init() {
		super.logger = this.logger;
	}

	public Activity getById(Long id) throws NoSuchItem {
		Activity activity = em.find(Activity.class, id);
		if (activity == null) {
			logger.error(properties.getProperty("err.activ.itemNF"));
			throw new NoSuchItem(MessageFormat.format(properties.getProperty("err.activ.nosuchitem"), id));
		}
		return activity;
	}

	public List<Activity> getAllForClient(Client client, int pageNumber, int countPerPage) {
		logger.info(MessageFormat.format(properties.getProperty("log.info.allactivs.client"), client.getId()));
		Query query = em.createNamedQuery("Activity.getAllForClient");
		query.setParameter("id", client.getId());
		return findAllForPage(query, pageNumber, countPerPage);
	}

	public int getItemCountForClient(Client client) {
		Query query = em.createNamedQuery("Activity.getItemCountForClient");
		query.setParameter("id", client.getId());
		int count = ((Long) query.getSingleResult()).intValue();
		logger.info(MessageFormat.format(properties.getProperty("log.info.activscount.client"),
				client.getId(), count));
		return count;
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	protected String getEntityName() {
		return Activity.class.getSimpleName();
	}
}
