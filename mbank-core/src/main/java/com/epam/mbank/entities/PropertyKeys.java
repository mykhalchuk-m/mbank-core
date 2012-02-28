package com.epam.mbank.entities;

public interface PropertyKeys {
	/**
	 * Regular new client deposit rate. In dollars.
	 * */
	String REGULAR_DEPOSIT_RATE = "regular_deposit_rate";
	/**
	 * Gold new client deposit rate. In dollars.
	 * */
	String GOLD_DEPOSIT_RATE = "gold_deposit_rate";
	/**
	 * Platinum new client deposit rate. In dollars.
	 * */
	String PLATINUM_DEPOSIT_RATE = "platinum_deposit_rate";
	/**
	 * Regular commission rate for deposit opening. In percents.
	 * */
	String REGULAR_DEPOSIT_COMMISSION = "regular_deposit_commission";
	/**
	 * Gold commission rate for deposit opening. In percents.
	 * */
	String GOLD_DEPOSIT_COMMISSION = "gold_deposit_commission";
	/**
	 * Platinum commission rate for deposit opening. In percents.
	 * */
	String PLATINUM_DEPOSIT_COMMISSION = "platinum_deposit_commission";
	/**
	 * Regular account overdraft limit (credit limit). In dollars.
	 * */
	String REGULAR_CREDIT_LIMIT = "regular_credit_limit";
	/**
	 * Gold account overdraft limit (credit limit). In dollars.
	 */
	String GOLD_CREDIT_LIMIT = "gold_credit_limit";
	/**
	 * Platinum account overdraft limit (credit limit). 0 means [unlimited].
	 * */
	String PLATINUM_CREDIT_LIMIT = "platinum_credit_limit";
	/**
	 * Commission rate for all withdraws & deposits. In percent.
	 * */
	String COMMISSION_RATE = "commission_rate";
	/**
	 * Regular daily percentage added deposit value.
	 * */
	String REGULAR_DAILY_INTEREST = "regular_daily_interest";
	/**
	 * Gold daily percentage added deposit value.
	 * */
	String GOLD_DAILY_INTEREST = "gold_daily_interest";
	/**
	 * Platinum daily percentage added deposit value.
	 * */
	String PLATINUM_DAILY_INTEREST = "platinum_daily_interest";
	/**
	 * Commission rate for deposit pre-opening. In percents.
	 * */
	String PRE_OPEN_FEE = "pre_open_fee";
	/**
	 * Default username for all system administrators
	 * */
	String ADMIN_USERNAME = "admin_username";
	/**
	 * Default password for all system administrators
	 * */
	String ADMIN_PASSWORD = "admin_password";
}
