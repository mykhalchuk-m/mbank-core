package com.epam.mbank.loggin.services;

import javax.ejb.Local;

import com.epam.mbank.loggin.entity.Log;

@Local
public interface LogProducer {

	public void registerLog(Log log);
	
	public void registerLog(Long clientId, String message);
}