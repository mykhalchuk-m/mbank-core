package com.epam.mbank.services;


public interface LoginService {
	/**
	 * Check if admin name and admin password match with data in properties
	 * table
	 */
	boolean adminLogin(String name, String pass);
}
