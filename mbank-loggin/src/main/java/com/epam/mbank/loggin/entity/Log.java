package com.epam.mbank.loggin.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table
public class Log implements Serializable {
	private static final long serialVersionUID = -3486065922897727470L;
	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "Log_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	@Column(name = "client_id")
	private Long clientId;
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	@Override
	public String toString() {
		return "Log [id=" + id + ", date=" + date + ", clientId=" + clientId + ", description=" + description
				+ "]";
	}

}
