package com.epam.mbank.admin.utils;

import java.util.LinkedList;
import java.util.List;

public class WebPaginationBuilder {
	public static List<String> getPagination(int currentPage, int itemsCount, int elementsPerPage) {
		List<String> pagination = new LinkedList<String>();
		long count;
		if (itemsCount % elementsPerPage == 0) {
			count = itemsCount / elementsPerPage;
		} else {
			count = itemsCount / elementsPerPage + 1;
		}
		if (count == 1) {
			return null;
		}
		if (count <= 5) {
			for (int i = 1; i <= count; i++) {
				pagination.add(String.valueOf(i));
			}
		} else {
			if (currentPage - 1 > 0) {
				if (currentPage > 2) {
					pagination.add(String.valueOf(1));
				}
				if (currentPage > 3) {
					pagination.add("");
				}
				pagination.add(String.valueOf(currentPage - 1));
			}
			pagination.add(String.valueOf(currentPage));
			if (currentPage + 1 <= count) {
				pagination.add(String.valueOf(currentPage + 1));
				long diff = count - currentPage;
				if (diff > 2) {
					pagination.add("");
				}
				if (diff > 1) {
					pagination.add(String.valueOf(count));
				}
			}
		}
		return pagination;
	}
}
