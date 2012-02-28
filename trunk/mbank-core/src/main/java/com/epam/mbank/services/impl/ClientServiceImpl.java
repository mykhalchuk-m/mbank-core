package com.epam.mbank.services.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.List;
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
import com.epam.mbank.dao.ClientManager;
import com.epam.mbank.dao.PropertiesManager;
import com.epam.mbank.entities.Account;
import com.epam.mbank.entities.Client;
import com.epam.mbank.entities.Deposit;
import com.epam.mbank.exception.NoSuchItem;
import com.epam.mbank.services.ActivityService;
import com.epam.mbank.services.ClientService;
import com.epam.mbank.services.DepositService;

@Stateless(name = "ClientService")
@Local(ClientService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ClientServiceImpl implements ClientService {
	private final static String LOG_MESSAGES_FILE = "logMessages.properties";
	private Properties properties;
	private final Logger logger = LoggerFactory.getLogger(getClass());

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
	
	@EJB
	private PropertiesManager propsManager;
	@EJB
	private ClientManager clientManager;
	@EJB
	private AccountManager accountManager;
	@EJB
	private ActivityService activityService;
	@EJB
	private DepositService depositService;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createClient(Client client) {
		logger.info(MessageFormat.format(properties.getProperty("info.client.creatr.s"), client.getName()));
		client.setClientType(propsManager.getClientType(client.getAccount().getBalance()));
		client.getAccount().setCreditLimit(propsManager.getCreditLimit(client.getClientType()));

		clientManager.save(client);
		logger.info(properties.getProperty("info.client.creatr.f"));
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void removeClient(Client client) throws NoSuchItem {
		logger.info(MessageFormat.format(properties.getProperty("info.client.rem.s"), client.getName()));

		List<Deposit> deposits = depositService.getDeposites(client, 1, depositService.getCountOfDeposits());
		for (Deposit deposit : deposits) {
			depositService.preOpenDeposit(client.getAccount(), deposit, true);
		}

		removeAccount(client.getAccount());
		clientManager.remove(client);
		logger.info(MessageFormat.format(properties.getProperty("info.client.rem.f"), client.getName()));
	}

	public void createAccount(Client client) {
		Account account = new Account();
		account.setClient(client);
		account.setCreditLimit(propsManager.getCreditLimit(client.getClientType()));
		accountManager.save(account);
	}

	public void removeAccount(Account account) {
		if (account.getBalance() < 0) {
			activityService.createRemAccActivity(account.getBalance(), account.getClient(),
					-account.getBalance());
		}
	}

	public void updateClient(Client client) throws NoSuchItem {
		clientManager.update(client);
	}

	public List<Client> getClients(int pageNumber, int countPerPage) {
		return clientManager.getAll(pageNumber, countPerPage);
	}

	public Client getClient(Long id) throws NoSuchItem {
		return clientManager.getById(id);
	}

	public List<Account> getAccounts(int pageNumber, int countPerPage) {
		return accountManager.getAll(pageNumber, countPerPage);
	}

	public int getCountOfAccounts() {
		return accountManager.getItemCount();
	}

	public int getCountOfClients() {
		return clientManager.getItemCount();
	}

	public Client getClient(String name) {
		return clientManager.getByName(name);
	}
}
