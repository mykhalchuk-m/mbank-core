package com.epam.mbank.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.epam.mbank.entities.validation.CreateClientGroup;

@Entity
@Table(name = "Clients")
@NamedQueries({
	@NamedQuery(name = "Client.getById", query = "from Client c where c.id=:id and c.deleted='false'"),
	@NamedQuery(name = "Client.getByNamePass", query = "from Client where client_name = :name and deleted='false'"),
	@NamedQuery(name = "Client.getAll", query = "from Client c where c.deleted='false'"),
	@NamedQuery(name = "Client.getItemCount", query = "select count(a) from Client a where a.deleted='false'")
})
public class Client implements Serializable {
	private static final long serialVersionUID = -4929563279653682203L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "client_id")
	private long id;
	@Column(name = "client_name")
	@Size(min = 1, message = "{client.name.size}", groups = CreateClientGroup.class)
	private String name;
	@Size(min = 6, message = "{client.password.size}", groups = CreateClientGroup.class)
	private String password;
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private ClientType clientType;
	private String address;
	@Pattern(regexp = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Provide correct email", groups = CreateClientGroup.class)
	private String email;
	private String phone;
	private String comment;
	@Valid
	@OneToOne(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Account account;
	@OneToMany(mappedBy = "client")
	private Set<Deposit> deposits;
	@OneToMany(mappedBy = "client")
	@OrderBy("activityDate desc")
	private Set<Activity> activities;
	private Boolean deleted;

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Set<Deposit> getDeposits() {
		return deposits;
	}

	public void setDeposits(Set<Deposit> deposits) {
		this.deposits = deposits;
	}

	public Set<Activity> getActivities() {
		return activities;
	}

	public void setActivities(Set<Activity> activities) {
		this.activities = activities;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ClientType getClientType() {
		return clientType;
	}

	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	@SuppressWarnings("unused")
	@PrePersist
	@PreUpdate
	private void correctDeleted() {
		if (deleted == null)
			deleted = Boolean.FALSE;
	}
}
