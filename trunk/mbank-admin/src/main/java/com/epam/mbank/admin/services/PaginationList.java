package com.epam.mbank.admin.services;

import java.util.List;

import com.epam.mbank.exception.NoSuchItem;

public interface PaginationList<T> {
	int ELEMENTS_PER_PAGE = 15;
	
	public List<T> getItemsPerPageForClient(Long id, int page) throws NoSuchItem;
	
	public List<T> getItemsPerPage(int page);
	
	public int getItemsCoutn(Long id);
	
	public int getItemsCountPerPage();
}
