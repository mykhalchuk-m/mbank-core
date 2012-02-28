package com.epam.mbank.services.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.mbank.dao.AccountManager;
import com.epam.mbank.dao.DepositManager;
import com.epam.mbank.dao.PropertiesManager;
import com.epam.mbank.entities.Account;
import com.epam.mbank.entities.Client;
import com.epam.mbank.entities.Deposit;
import com.epam.mbank.entities.DepositType;
import com.epam.mbank.entities.PropertyKeys;
import com.epam.mbank.entities.validation.CreateDepositGroup;
import com.epam.mbank.exception.NoSuchItem;
import com.epam.mbank.services.DepositService;
import com.epam.mbank.services.MoneyTransactionService;
import com.epam.mbank.services.PropertiesService;

@Stateless(name = "DepositService")
@Local(DepositService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DepositServiceImpl implements DepositService {
	private final static String LOG_MESSAGES_FILE = "logMessages.properties";
	private Properties properties;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@EJB
	private PropertiesManager propsManager;
	@EJB
	private DepositManager depositManager;
	@EJB
	private AccountManager accountManager;
	@EJB
	private PropertiesService propertiesService;
	@EJB
	private MoneyTransactionService mtService;

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

	public void createNewDeposite(Client client, double amount, Date closeDate) throws NoSuchItem {
		logger.info(MessageFormat.format(properties.getProperty("info.depos.create.s"), client.getName()));
		Deposit deposit = new Deposit();
		deposit.setBalance(amount);
		deposit.setClosingDate(closeDate);
		deposit.setClient(client);
		Date startDate = new Date(System.currentTimeMillis());
		deposit.setOpeningDate(startDate);
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		Calendar finish = Calendar.getInstance();
		finish.setTime(closeDate);
		int days = finish.get(Calendar.DAY_OF_YEAR) + 365
				* (finish.get(Calendar.YEAR) - start.get(Calendar.YEAR)) - start.get(Calendar.DAY_OF_YEAR);
		if (days > 365 && days < 14610) {
			deposit.setType(DepositType.LONG);
		} else if (days < 365 && days > 0) {
			deposit.setType(DepositType.SHORT);
		}
		logger.info(MessageFormat.format(properties.getProperty("info.depos.create.duration"), days));
		double esimatedIncrease = amount * propsManager.getDepositInterest(client.getClientType())
				* (double) days;
		logger.info(MessageFormat.format(properties.getProperty("info.depos.create.inc"), esimatedIncrease));
		deposit.setEstimatedBalance(amount + esimatedIncrease);
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Deposit>> violations = validator.validate(deposit, CreateDepositGroup.class);
		if (violations.size() > 0) {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}
		depositManager.save(deposit);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void preOpenDeposit(Account account, Deposit deposit, boolean all) throws NoSuchItem {
		if (all && (deposit.getType() != DepositType.SHORT)) {
			return;
		}
		double preOpenFee = Double.parseDouble(propertiesService
				.viewSystemProperty(PropertyKeys.PRE_OPEN_FEE));
		double balance = deposit.getBalance();
		account.setBalance(account.getBalance() + balance - balance * preOpenFee);
		accountManager.update(account);
		deposit.setClosingDate(new Date());
		depositManager.remove(deposit);
		mtService.incBankBalance(balance * preOpenFee);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void closeDeposit(Deposit deposit) {
		Account account = deposit.getClient().getAccount();
		account.setBalance(account.getBalance() + deposit.getBalance());
		accountManager.update(account);
		depositManager.remove(deposit);
	}

	public List<Deposit> getDeposits(int pageNumber, int countPerPage) {
		return depositManager.getAll(pageNumber, countPerPage);
	}

	public List<Deposit> getDeposites(Client client, int pageNumber, int countPerPage) throws NoSuchItem {
		return depositManager.getAllForClient(client, pageNumber, countPerPage);
	}

	public int getCountOfDeposits() {
		return depositManager.getItemCount();
	}

	public int getCountOfDeposits(Client client) {
		return depositManager.getItemCountForClient(client);
	}

	public Deposit getDeposit(Long id) throws NoSuchItem {
		return depositManager.getById(id);
	}

}
