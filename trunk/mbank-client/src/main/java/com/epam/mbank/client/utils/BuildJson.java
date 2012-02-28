package com.epam.mbank.client.utils;

import java.util.List;

import com.epam.mbank.entities.Activity;
import com.epam.mbank.entities.Client;

public class BuildJson {
	public static String buildJsonFromActivity(List<Activity> activities) {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"activities\":[");
		int count = activities.size();
		for (int i = 0; i < count; i++) {
			Activity activity = activities.get(i);
			sb.append("{");
			sb.append("\"client\": \"" + activity.getClient().getName() + "\",");
			sb.append("\"amount\": \"" + activity.getAmount() + "\",");
			sb.append("\"commission\": \"" + activity.getCommission() + "\",");
			sb.append("\"activityDate\": \"" + activity.getActivityDate()
					+ "\",");
			sb.append("\"description\": \"" + activity.getDescription() + "\"");
			if (i + 1 == count) {
				sb.append("}");
			} else {
				sb.append("},");
			}
		}
		sb.append("]}");
		return sb.toString();
	}

	public static String buildJsonFromClient(Client client) {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"id\":\"" + client.getId() + "\",");
		sb.append("\"name\":\"" + client.getName() + "\",");
		sb.append("\"email\":\"" + client.getEmail() + "\",");
		sb.append("\"password\":\"" + client.getPassword() + "\",");
		sb.append("\"phone\":\"" + client.getPhone() + "\",");
		sb.append("\"address\":\"" + client.getAddress() + "\",");
		sb.append("\"comment\":\"" + client.getComment() + "\",");
		sb.append("\"balance\":\"" + client.getAccount().getBalance() + "\",");
		sb.append("\"clientType\":\"" + client.getClientType() + "\"}");
		return sb.toString();
	}
}
