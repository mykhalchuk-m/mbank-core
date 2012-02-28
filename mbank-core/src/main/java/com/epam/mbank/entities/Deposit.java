package com.epam.mbank.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;

import com.epam.mbank.entities.validation.CreateDepositGroup;

@Entity
@Table(name = "Deposits")
@NamedQueries({
		@NamedQuery(name = "Deposit.getById", query = "from Deposit d where d.id=:id and d.client.deleted='false'"),
		@NamedQuery(name = "Deposit.getAllForClient", query = "from Deposit where client.id=:id and client.deleted='false'"),
		@NamedQuery(name = "Deposit.getItemCountForClient", query = "select count(a) from Deposit a where a.client.id = :id and a.client.deleted='false'"),
		@NamedQuery(name = "Deposit.getItemCount", query = "select count(a) from Deposit a where a.client.deleted='false'"),
		@NamedQuery(name = "Deposit.getAll", query = "from Deposit where client.deleted='false'") })
public class Deposit implements Serializable {
	private static final long serialVersionUID = -6635983193309632823L;

	@Id
	@GeneratedValue
	@Column(name = "deposit_id")
	private long id;
	@Min(value = 0, message = "{deposit.balance.min}", groups = CreateDepositGroup.class)
	private double balance;
	@Enumerated(EnumType.STRING)
	private DepositType type;
	@Column(name = "estimated_balance")
	private double estimatedBalance;
	@Temporal(TemporalType.DATE)
	@Column(name = "opening_date")
	private Date openingDate;
	@Future(message = "{deposit.closingDate.future}", groups = CreateDepositGroup.class)
	@Temporal(TemporalType.DATE)
	@Column(name = "closing_date")
	private Date closingDate;
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public DepositType getType() {
		return type;
	}

	public void setType(DepositType type) {
		this.type = type;
	}

	public double getEstimatedBalance() {
		return estimatedBalance;
	}

	public void setEstimatedBalance(double estimatedBalance) {
		this.estimatedBalance = estimatedBalance;
	}

	public Date getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}

	public Date getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}
