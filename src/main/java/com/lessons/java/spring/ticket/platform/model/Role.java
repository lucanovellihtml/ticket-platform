package com.lessons.java.spring.ticket.platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

//entità roles
/**
 * Relazionata con User(Admin-Operator) e Role
 */
@Entity
@Table(name = "roles")
public class Role {

	@Id
	private Integer id;

	@NotNull
	private String name;

	// getter - setter
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
