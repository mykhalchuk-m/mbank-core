package com.epam.mbank.client.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class Menu {
	private static Map<String, String> menu = new LinkedHashMap<String, String>();

	static {
		menu.put("Home", "/mbank-client");
		menu.put("My Details", "/mbank-client/details");
		menu.put("My Deposits", "/mbank-client/deposits");
		menu.put("My Activities", "/mbank-client/activities");
	}

	public Map<String, String> getMenu() {
		return menu;
	}
}
