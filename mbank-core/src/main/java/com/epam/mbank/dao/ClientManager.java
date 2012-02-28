package com.epam.mbank.dao;

import java.util.List;

import javax.ejb.Local;

import com.epam.mbank.entities.Client;

@Local
public interface ClientManager extends GenericDAO<Client, Long> {
	List<Client> getAll(int pageNumber, int countPerPage);

	Client getByName(String name);
}
