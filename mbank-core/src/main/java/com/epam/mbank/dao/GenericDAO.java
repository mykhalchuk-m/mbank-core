package com.epam.mbank.dao;

import com.epam.mbank.exception.NoSuchItem;

public interface GenericDAO<T, K> {
	T getById(K id) throws NoSuchItem;

	int getItemCount();
	
	void save(T t);

	void remove(T t);

	T update(T t);
}
