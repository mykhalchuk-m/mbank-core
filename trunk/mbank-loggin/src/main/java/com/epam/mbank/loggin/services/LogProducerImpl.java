package com.epam.mbank.loggin.services;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.PrePassivate;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import com.epam.mbank.loggin.entity.Log;

@Stateless(name = "LogProducer")
public class LogProducerImpl implements LogProducer {
	@Resource(lookup = "/ConnectionFactory")
	private ConnectionFactory connectionFactory;

	@Resource(lookup = "/queue/testQueue")
	private Queue queue;

	private Session session = null;
	private Connection connection = null;

	@SuppressWarnings("unused")
	@PostConstruct
	private void init() throws JMSException {
		connection = connectionFactory.createConnection();
	}

	public void registerLog(Log log) {
		try {
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			ObjectMessage message = session.createObjectMessage(log);
			MessageProducer producer = session.createProducer(queue);
			producer.send(message);
			producer.close();
			closeSession();
		} catch (JMSException e) {
			e.printStackTrace();
			closeSession();
		} 
	}
	
	@PreDestroy
	@PrePassivate
	private void closeSession() {
		if (session != null) {
			try {
				session.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

	public void registerLog(Long clientId, String message) {
		Log log = new Log();
		log.setClientId(clientId);
		log.setDate(new Date());
		log.setDescription(message);
		registerLog(log);
	}
}
