package com.epam.mbank.services.impl;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.epam.mbank.dao.PropertiesManager;
import com.epam.mbank.entities.Property;
import com.epam.mbank.exception.NoSuchItem;
import com.epam.mbank.services.PropertiesService;

@Stateless(name = "PropertiesService")
@Local(PropertiesService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PropertiesServiceImpl implements PropertiesService {
	@EJB
	private PropertiesManager propsManager;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateSystemProperty(String name, String value) throws NoSuchItem {
		Property property = propsManager.getById(name);
		property.setValue(value);
		propsManager.update(property);
	}

	public String viewSystemProperty(String name) throws NoSuchItem {
		return propsManager.getById(name).getValue();
	}

}
