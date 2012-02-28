package com.epam.mbank.loggin.services.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.epam.mbank.loggin.entity.Log;

@Stateless
public class LogDBManager implements LogManager {
	
	@PersistenceContext(name = "log")
	private EntityManager em;

	public void save(Log log) {
		em.persist(log);
	}
}
