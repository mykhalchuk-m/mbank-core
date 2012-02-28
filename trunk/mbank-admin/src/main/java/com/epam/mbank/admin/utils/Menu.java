package com.epam.mbank.admin.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class Menu {
	private static Map<String, String> menu = new LinkedHashMap<String, String>();

	static {
		menu.put("Home", "/mbank-admin");
		menu.put("Clients", "/mbank-admin/clients");
		menu.put("Deposits", "/mbank-admin/deposits");
		menu.put("Accounts", "/mbank-admin/accounts");
		menu.put("Activities", "/mbank-admin/activities");
	}

	public Map<String, String> getMenu() {
		return menu;
	}
}
