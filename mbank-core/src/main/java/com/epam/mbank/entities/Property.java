package com.epam.mbank.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Properties")
public class Property implements Serializable {
	private static final long serialVersionUID = 5769439365749672837L;

	@Id
	@Column(name = "prop_key")
	private String name;
	@Column(name = "prop_value")
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
