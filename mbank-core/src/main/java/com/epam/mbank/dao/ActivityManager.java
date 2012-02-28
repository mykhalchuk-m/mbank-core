package com.epam.mbank.dao;

import java.util.List;

import javax.ejb.Local;

import com.epam.mbank.entities.Activity;
import com.epam.mbank.entities.Client;

@Local
public interface ActivityManager extends GenericDAO<Activity, Long> {
	List<Activity> getAll(int pageNumber, int countPerPage);

	List<Activity> getAllForClient(Client client, int pageNumber, int countPerPage);

	int getItemCountForClient(Client client);
}
