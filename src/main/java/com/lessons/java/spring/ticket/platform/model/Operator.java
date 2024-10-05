package com.lessons.java.spring.ticket.platform.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

//entità operators
@Entity
@Table(name = "operators")
public class Operator {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	private String email;

	@NotNull
	private String status;

	@NotNull
	private String password;

	/**
	 * relazione dove un operatore puo' avere piu' zero,uno,molti tickets
	 */
	@OneToMany(mappedBy = "operator", cascade = { CascadeType.REMOVE })
	@JsonBackReference // permette di aggirare la ricorsione infinita tra le entità
	private List<Ticket> tickets;

	// TODO ricordarsi di aggiungere la relazione tra operator e role

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

}
