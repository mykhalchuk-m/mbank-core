package com.epam.mbank.admin.utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.epam.mbank.entities.PropertyKeys;
import com.epam.mbank.entities.Property;

public class SettingsKeys {
	private Map<String, String> tempMap = new LinkedHashMap<String, String>();
		
	public SettingsKeys() {
		init();
	}
	
	private void init() {
		tempMap.put(PropertyKeys.ADMIN_PASSWORD, "Admin password");
		tempMap.put(PropertyKeys.ADMIN_USERNAME, "Admin username");
		tempMap.put(PropertyKeys.COMMISSION_RATE, "Commision rate");
		tempMap.put(PropertyKeys.GOLD_CREDIT_LIMIT, "Gold credit limit");
		tempMap.put(PropertyKeys.GOLD_DAILY_INTEREST, "Gold daily interest");
		tempMap.put(PropertyKeys.GOLD_DEPOSIT_COMMISSION, "Gold deposit commission");
		tempMap.put(PropertyKeys.GOLD_DEPOSIT_RATE, "Gold deposit rate");
		tempMap.put(PropertyKeys.PLATINUM_CREDIT_LIMIT, "Platinum credit limit");
		tempMap.put(PropertyKeys.PLATINUM_DAILY_INTEREST, "Platinum daily interest");
		tempMap.put(PropertyKeys.PLATINUM_DEPOSIT_COMMISSION, "Platinum deposit commission");
		tempMap.put(PropertyKeys.PLATINUM_DEPOSIT_RATE, "Platinum deposit rate");
		tempMap.put(PropertyKeys.PRE_OPEN_FEE, "Pre open fee");
		tempMap.put(PropertyKeys.REGULAR_CREDIT_LIMIT, "Regular credit limit");
		tempMap.put(PropertyKeys.REGULAR_DAILY_INTEREST, "Regular daily interest");
		tempMap.put(PropertyKeys.REGULAR_DEPOSIT_COMMISSION, "Regular deposit commission");
		tempMap.put(PropertyKeys.REGULAR_DEPOSIT_RATE, "Regular deposit rate");
	}
	
	public Map<String, Property> getWebKeys(List<Property> properties) {
		Map<String, Property> keys = new LinkedHashMap<String, Property>();
		for (Property property : properties) {
			keys.put(tempMap.get(property.getName()), property);
		}
		return keys;
	}
	
	public Set<String> getPropertyKeys() {
		return tempMap.keySet();
	}
}
