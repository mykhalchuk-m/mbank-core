package com.epam.mbank.dao;

import java.util.List;

import com.epam.mbank.entities.Client;
import com.epam.mbank.entities.ClientType;
import com.epam.mbank.entities.Property;

public interface PropertiesManager extends GenericDAO<Property, String> {
	double getCreditLimit(ClientType clientType);

	double getDepositCommission(ClientType clientType);

	double getDepositInterest(ClientType clientType);

	ClientType getClientType(double amount);
	
	void checkClientType(Client client);
	
	List<Property> getAll();
}
