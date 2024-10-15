package com.lessons.java.spring.ticket.platform.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

//entità categories
@Entity
@Table(name = "categories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique=true)
	@NotNull(message="The title of category cannot be null!")
	@Size(min = 2, max = 255, message="Title must have at least 2 charachters and a maximum of 255")
	private String title;
	
	private String description;
	
	/**
	 * relazione dove a una category possono esserci zero,uno,molti tickets
	 */
	@OneToMany(mappedBy = "category", cascade = { CascadeType.REMOVE })
	@JsonBackReference // permette di aggirare la ricorsione infinita tra le entità
	private List<Ticket> tickets;

	// getter - setter
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
}
