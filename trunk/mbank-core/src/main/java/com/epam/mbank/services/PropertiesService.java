package com.epam.mbank.services;

import com.epam.mbank.exception.NoSuchItem;

public interface PropertiesService {

	/**
	 * Update system property. Throw <code>NoSuchItem</code> exception when
	 * there is to such property
	 * 
	 * @throws NoSuchItem
	 */
	void updateSystemProperty(String name, String value) throws NoSuchItem;

	/**
	 * View system property. Throw <code>NoSuchItem</code> exception when there
	 * is to such property
	 * 
	 * @throws NoSuchItem
	 */
	String viewSystemProperty(String name) throws NoSuchItem;
}
