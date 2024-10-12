package com.lessons.java.spring.ticket.platform.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

//entità tickets
@Entity
@Table(name = "tickets")
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull(message="The name of ticket cannot be null!")
	@Size(min = 2, max = 255, message="Name must have at least 2 charachters and a maximum of 255")
	private String name;
	
	@NotNull(message="The status of ticket cannot be null!")
	private String status;

	/**
	 * relazione dove un ticket puo' avere piu' zero,uno,molti note
	 */
	@OneToMany(mappedBy = "ticket", cascade = { CascadeType.REMOVE })
	@JsonBackReference // permette di aggirare la ricorsione infinita tra le entità
	private List<Note> notes;

	/**
	 * relazione dove un ticket puo' avere un solo User
	 */
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@JsonBackReference
	private User user;

	/**
	 * relazione dove un ticket puo' avere una sola category
	 */
	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	@JsonBackReference
	private Category category;
	
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
