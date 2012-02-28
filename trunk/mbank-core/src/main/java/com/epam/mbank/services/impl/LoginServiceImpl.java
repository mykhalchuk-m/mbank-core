package com.epam.mbank.services.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.mbank.entities.PropertyKeys;
import com.epam.mbank.exception.NoSuchItem;
import com.epam.mbank.services.LoginService;
import com.epam.mbank.services.PropertiesService;

@Stateless(name = "LoginService")
@Local(LoginService.class)
public class LoginServiceImpl implements LoginService {
	private final static String LOG_MESSAGES_FILE = "logMessages.properties";
	private Properties properties;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@EJB
	private PropertiesService propertiesService;

	@SuppressWarnings("unused")
	@PostConstruct
	private void init() {
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

	public boolean adminLogin(String name, String pass) {
		try {
			String dbName = propertiesService.viewSystemProperty(PropertyKeys.ADMIN_USERNAME);
			String dbPass = propertiesService.viewSystemProperty(PropertyKeys.ADMIN_PASSWORD);
			logger.info(MessageFormat.format(properties.getProperty("info.admin.login"), dbName, dbPass));
			return dbName.equals(name) && dbPass.endsWith(pass);
		} catch (NoSuchItem e) {
			return false;
		}
	}

}
