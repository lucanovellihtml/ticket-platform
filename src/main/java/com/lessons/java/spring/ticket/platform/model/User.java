package com.lessons.java.spring.ticket.platform.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;

//entità user
@Entity
public class User {

	@Id
	private Integer id;

	@NotNull
	private String email;

	@NotNull
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles;

	public Integer getId() {
		return id;
	}

	// getter - setter
	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
