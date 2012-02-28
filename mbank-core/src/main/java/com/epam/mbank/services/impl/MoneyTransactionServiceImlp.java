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
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.mbank.dao.AccountManager;
import com.epam.mbank.entities.Account;
import com.epam.mbank.entities.PropertyKeys;
import com.epam.mbank.exception.NoSuchItem;
import com.epam.mbank.exception.OperationNotAllowedException;
import com.epam.mbank.services.ActivityService;
import com.epam.mbank.services.MoneyTransactionService;
import com.epam.mbank.services.PropertiesService;

@Stateless(name = "MoneyTransactionService")
@Local(MoneyTransactionService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MoneyTransactionServiceImlp implements MoneyTransactionService {
	private final static String LOG_MESSAGES_FILE = "logMessages.properties";
	private Properties properties;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@EJB
	private PropertiesService propertiesService;
	@EJB
	private AccountManager accountManager;
	@EJB
	private ActivityService activityService;

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

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void withdraw(Account account, double amount) throws OperationNotAllowedException, NoSuchItem {
		if (amount < 0)
			throw new OperationNotAllowedException(properties.getProperty("err.money.notallowop"));
		double commisionRate = Double.parseDouble(propertiesService
				.viewSystemProperty(PropertyKeys.COMMISSION_RATE));
		double limit = account.getCreditLimit();
		if (amount + commisionRate > limit + account.getBalance() && limit > 0)
			throw new OperationNotAllowedException(MessageFormat.format(
					properties.getProperty("err.money.notallowop.lomit"), limit));
		double balance = account.getBalance() - amount - commisionRate;
		logger.info(MessageFormat.format(properties.getProperty("info.money.resbalance"), balance));
		account.setBalance(balance);
		accountManager.update(account);
		activityService.createWithdrawActivity(amount, account.getClient(), commisionRate);
		incBankBalance(commisionRate);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deposit(Account account, double amount) throws NoSuchItem, OperationNotAllowedException {
		if (amount < 0)
			throw new OperationNotAllowedException(properties.getProperty("err.money.notallowop"));
		double commisionRate = Double.parseDouble(propertiesService
				.viewSystemProperty(PropertyKeys.COMMISSION_RATE));
		account.setBalance(account.getBalance() + amount - commisionRate);
		activityService.createDepositActivity(amount, account.getClient(), commisionRate);
		accountManager.update(account);
		incBankBalance(commisionRate);
	}

	public void incBankBalance(double amount) {
		logger.info(MessageFormat.format(properties.getProperty("info.bomey.bank"), amount));
	}

}
