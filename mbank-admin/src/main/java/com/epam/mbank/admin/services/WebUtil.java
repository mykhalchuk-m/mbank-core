package com.epam.mbank.admin.services;

import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;

import com.epam.mbank.entities.Client;
import com.epam.mbank.exception.NoSuchItem;

@Local
public interface WebUtil {

	public Client createClientWithAccount(HttpServletRequest request);

	public Client createClientForEdidting(HttpServletRequest request)
			throws NoSuchItem;

	public void getClientDeposits(HttpServletRequest request);

	public void getClientActivity(HttpServletRequest request);

}