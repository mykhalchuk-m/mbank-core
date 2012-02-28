package com.epam.mbank.loggin.services;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.epam.mbank.loggin.entity.Log;
import com.epam.mbank.loggin.services.dao.LogManager;

@MessageDriven(name = "LogListener", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/testQueue") })
public class LogListener implements MessageListener {

	@EJB
	private LogManager logManager;

	public void onMessage(Message message) {
		try {
			ObjectMessage logMessage = (ObjectMessage) message;
			Log log = (Log) logMessage.getObject();
			logManager.save(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
