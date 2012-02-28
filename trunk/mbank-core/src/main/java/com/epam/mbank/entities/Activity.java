package com.epam.mbank.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table
@NamedQueries({
		@NamedQuery(name = "Activity.getItemCount", query = "select count(a) from Activity a"),
		@NamedQuery(name = "Activity.getAllForClient", query = "from Activity where client.id = :id and client.deleted='false' order by activityDate desc"),
		@NamedQuery(name = "Activity.getItemCountForClient", query = "select count(a) from Activity a where a.client.id = :id and a.client.deleted='false'"),
		@NamedQuery(name = "Activity.getAll", query = "from Activity a order by a.activityDate desc") })
public class Activity implements Serializable {
	private static final long serialVersionUID = -2909340260743053911L;

	@Id
	@GeneratedValue
	private long id;
	private double amount;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "activity_date")
	private Date activityDate;
	private double commission;
	private String description;
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}
