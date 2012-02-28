package com.epam.mbank.loggin.services.dao;

import javax.ejb.Local;

import com.epam.mbank.loggin.entity.Log;

@Local
public interface LogManager {

	public void save(Log log);

}