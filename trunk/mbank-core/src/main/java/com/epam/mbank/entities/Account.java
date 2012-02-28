package com.epam.mbank.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.epam.mbank.entities.validation.CreateClientGroup;

@Entity
@Table(name = "Accounts")
@NamedQueries({
		@NamedQuery(name = "Account.getItemCount", query = "select count(a) from Account a where a.client.deleted='false'"),
		@NamedQuery(name = "Account.getById", query = "from Account a where a.id=:id and a.client.deleted='false'"),
		@NamedQuery(name = "Account.getAll", query = "from Account a where a.client.deleted='false'") })
public class Account implements Serializable {
	private static final long serialVersionUID = 5507707684448122308L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "account_id")
	private long id;
	@NotNull
	@Min(value = 0, message = "{account.balance.min}", groups = CreateClientGroup.class)
	private double balance;
	@Column(name = "credit_limit")
	private double creditLimit;
	private String comment;
	@OneToOne
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

	public double getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(double creditLimit) {
		this.creditLimit = creditLimit;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}
