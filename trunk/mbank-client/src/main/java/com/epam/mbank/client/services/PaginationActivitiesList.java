package com.epam.mbank.client.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import com.epam.mbank.entities.Activity;
import com.epam.mbank.entities.Client;
import com.epam.mbank.exception.NoSuchItem;
import com.epam.mbank.services.ActivityService;
import com.epam.mbank.services.ClientService;

@Stateless(name = "client/PaginationActivitiesList")
@Local(PaginationList.class)
public class PaginationActivitiesList implements PaginationList<Activity> {
	@EJB(mappedName = "ClientService/local")
	private ClientService clientService;

	@EJB(mappedName = "ActivityService/local")
	private ActivityService activityService;

	public int getItemsCountPerPage() {
		return ELEMENTS_PER_PAGE;
	}

	public int getItemsCoutn(Long id) throws NoSuchItem {
		Client client = clientService.getClient(id);
		return activityService.getCountOfActivities(client);
	}

	public List<Activity> getItemsPerPage(Long id, int page) throws NoSuchItem {
		Client client = clientService.getClient(id);
		return activityService.getActivities(client, page, ELEMENTS_PER_PAGE);
	}
}
