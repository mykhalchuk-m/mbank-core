package com.epam.mbank.admin.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import com.epam.mbank.entities.Activity;
import com.epam.mbank.entities.Client;
import com.epam.mbank.exception.NoSuchItem;
import com.epam.mbank.services.ActivityService;
import com.epam.mbank.services.ClientService;

@Stateless(name = "PaginationActivitiesList")
@Local(PaginationList.class)
public class PaginationActivitiesList implements PaginationList<Activity> {
	@EJB(mappedName = "ClientService/local")
	private ClientService clientService;
	@EJB(mappedName = "ActivityService/local")
	private ActivityService activityService;

	public List<Activity> getItemsPerPageForClient(Long id, int pageNumber) throws NoSuchItem {
		Client client = clientService.getClient(id);
		return activityService.getActivities(client, pageNumber, ELEMENTS_PER_PAGE);
	}

	public List<Activity> getItemsPerPage(int page) {
		return activityService.getActivities(page, ELEMENTS_PER_PAGE);
	}

	public int getItemsCountPerPage() {
		return ELEMENTS_PER_PAGE;
	}

	public int getItemsCoutn(Long id) {
		try {
			return id == null ? activityService.getCountOfActivities() : activityService
					.getCountOfActivities(clientService.getClient(id));
		} catch (NoSuchItem e) {
			return activityService.getCountOfActivities();
		}
	}
}
