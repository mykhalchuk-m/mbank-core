package com.epam.mbank.services;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;

import com.epam.mbank.entities.Deposit;


@Stateless
public class DailyDepositChecker {

	@EJB(mappedName = "DepositService/local")
	private DepositService depositService;
	
	@Schedule(hour = "23", minute = "59", second = "59", dayOfWeek = "*")
	public void checkDeposits(final Timer timer) {
		List<Deposit> deposits = depositService.getDeposits(1, Integer.MAX_VALUE);

		for (Deposit deposit : deposits) {
			if (deposit.getClosingDate().after(new Date()))
				depositService.closeDeposit(deposit);
		}
	}
}
