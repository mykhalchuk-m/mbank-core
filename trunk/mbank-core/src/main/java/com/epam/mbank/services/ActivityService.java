package com.epam.mbank.services;

import java.util.List;

import com.epam.mbank.entities.Activity;
import com.epam.mbank.entities.Client;
import com.epam.mbank.exception.NoSuchItem;

public interface ActivityService {
	/**
	 * Create activity and save it in data base when client did deposit to
	 * account balance
	 */
	void createDepositActivity(double amount, Client client, double commission);

	/**
	 * Create activity and save it in data base when client did withdraw from
	 * account balance
	 */
	void createWithdrawActivity(double amount, Client client, double commission);

	/**
	 * Create activity and save it in data base when client remove his account
	 * and account balance is negative
	 */
	void createRemAccActivity(double amount, Client client, double commission);


	/**
	 * Return all activities for some page with given count of items per page
	 */
	List<Activity> getActivities(int pageNumber, int countPerPage);

	/**
	 * Return all activities for some page with given count of items per page
	 * for one client. If client not exist in data base then throws
	 * <code>NoSuchItem</code>
	 * 
	 * @throws NoSuchItem
	 */
	List<Activity> getActivities(Client client, int pageNumber, int countPerPage) throws NoSuchItem;
	
	/**
	 * Return last <code>limit</code> activities.
	 */ 
	List<Activity> getLastLimActs(int limit);
	
	/**
	 * Return last <code>limit</code> activities for some client. If client not
	 * exist in data base then throws <code>NoSuchItem</code>
	 * 
	 * @throws NoSuchItem
	 */
	List<Activity> getLastLimActs(Client client, int limit) throws NoSuchItem;
	
	/**
	 * Return count of all activities in data base.
	 */
	int getCountOfActivities();

	/**
	 * Get count of all activities for followed client
	 */
	public int getCountOfActivities(Client client);

}