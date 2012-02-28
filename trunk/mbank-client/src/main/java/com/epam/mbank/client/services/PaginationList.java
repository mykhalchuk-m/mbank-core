package com.epam.mbank.client.services;

import java.util.List;

import com.epam.mbank.exception.NoSuchItem;

public interface PaginationList<T> {
	int ELEMENTS_PER_PAGE = 15;
	
	public List<T> getItemsPerPage(Long id, int page) throws NoSuchItem;
	
	public int getItemsCoutn(Long id) throws NoSuchItem;
	
	public int getItemsCountPerPage();
}
