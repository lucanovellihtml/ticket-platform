package com.lessons.java.spring.ticket.platform.model;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique=true)
	@NotNull(message="The email of user cannot be null!")
	@NotEmpty(message="The email of user cannot be empty!")
	@Size(min = 2, max = 255, message="Email must have at least 2 charachters and a maximum of 255")
	private String email;
	
	@Column(unique=true)
	@NotNull(message="The password of user cannot be null!")
	@NotEmpty(message="The password of user cannot be empty!")
	@Size(min = 2, max = 255, message="Password must have at least 2 charachters and a maximum of 255")
	private String password;
	
	/**
	 * Lo status ho deciso di impostare nessuna condizione di validazione del valore perchè distinguere admin da operator
	 */
	private Boolean status;

	/**
	 * relazione dove un operatore puo' avere piu' zero,uno,molti tickets
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE })
	@JsonBackReference // permette di aggirare la ricorsione infinita tra le entità
	private List<Ticket> tickets;

	
	/**
	 * Relazione per la configurazione della security
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles;

	
	// getter - setter
	public Integer getId() {
		return id;
	}

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
	
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

}
