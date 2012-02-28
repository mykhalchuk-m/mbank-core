package com.epam.mbank.services.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import com.epam.mbank.dao.ActivityManager;
import com.epam.mbank.entities.Activity;
import com.epam.mbank.entities.Client;
import com.epam.mbank.exception.NoSuchItem;
import com.epam.mbank.services.ActivityService;

@Stateless(name = "ActivityService")
@Local(ActivityService.class)
public class ActivityServiceImpl implements ActivityService {
	private final static String LOG_MESSAGES_FILE = "logMessages.properties";
	private Properties properties;
	
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
	private ActivityManager activityManager;

	public void createDepositActivity(double amount, Client client, double commission) {
		Activity activity = crateActivity(amount, client, commission);
		activity.setDescription(properties.getProperty("desc.activ.deposit"));
		activityManager.save(activity);
	}

	public void createWithdrawActivity(double amount, Client client, double commission) {
		Activity activity = crateActivity(amount, client, commission);
		activity.setDescription(properties.getProperty("desc.activ.withdr"));
		activityManager.save(activity);
	}

	public void createRemAccActivity(double amount, Client client, double commission) {
		Activity activity = crateActivity(amount, client, commission);
		activity.setDescription(properties.getProperty("desc.activ.remaccount"));
		activityManager.save(activity);
	}

	private Activity crateActivity(double amount, Client client, double commission) {
		Activity activity = new Activity();
		activity.setActivityDate(new Date(System.currentTimeMillis()));
		activity.setAmount(amount);
		activity.setClient(client);
		activity.setCommission(commission);
		return activity;
	}

	public List<Activity> getActivities(int pageNumber, int countPerPage) {
		return activityManager.getAll(pageNumber, countPerPage);
	}

	public List<Activity> getActivities(Client client, int pageNumber, int countPerPage) throws NoSuchItem {
		return activityManager.getAllForClient(client, pageNumber, countPerPage);
	}

	public List<Activity> getLastLimActs(int limit) {
		return activityManager.getAll(1, limit);
	}

	public List<Activity> getLastLimActs(Client client, int limit) throws NoSuchItem {
		return activityManager.getAllForClient(client, 1, limit);
	}

	public int getCountOfActivities() {
		return activityManager.getItemCount();
	}

	public int getCountOfActivities(Client client) {
		return activityManager.getItemCountForClient(client);
	}
}
