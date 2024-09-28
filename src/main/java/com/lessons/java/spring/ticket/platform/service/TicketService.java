package com.lessons.java.spring.ticket.platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lessons.java.spring.ticket.platform.model.Ticket;
import com.lessons.java.spring.ticket.platform.repo.TicketRepository;

@Service
public class TicketService {

	@Autowired
	private TicketRepository repository;

	/**
	 * 
	 * @return la lista dei tickets presenti nel repository
	 */
	public List<Ticket> findAllTickets() {

		return repository.findAll();

	}

	/**
	 * 
	 * @param name, filto di ricerca del ticket;
	 * @return la lista dei tickets che contentono il nome della ricerca;
	 */
	public List<Ticket> findAllByName(String name) {

		return repository.findByName(name);

	}

	/**
	 * 
	 * @param id, id del ticket;
	 * @return tutte le informazioni del ticket specifico;
	 */
	public Ticket getById(int id) {

		return repository.findById(id).get();

	}

}
