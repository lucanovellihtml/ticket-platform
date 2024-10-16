package com.lessons.java.spring.ticket.platform.service;

import java.util.ArrayList;
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
	 * @return la lista dei tickets presenti nel repository
	 */
	public List<Ticket> findAllTickets() {

		return repository.findAll();

	}

	/**
	 * @param category, filtro della ricerca del ticket
	 * @return la lista dei tickets con il filtro sulla category
	 */
	public List<Ticket> findAllByCategory(String category) {

		List<Ticket> listTicketsAll;
		List<Ticket> listTicketsFilter = new ArrayList<Ticket>();

		listTicketsAll = repository.findAll();

		for (Ticket ticket : listTicketsAll) {

			// Controllo se il singolo ticket ciclato ha il titolo dell'input category
			// uguale
			// Se supera la condizione, viene popolato la lista nuova filtrata
			if (ticket.getCategory().getTitle().equals(category))
				listTicketsFilter.add(ticket);

		}

		return listTicketsFilter;

	}

	/**
	 * 
	 * @param name, filto di ricerca del ticket
	 * @return la lista dei tickets che contentono il nome della ricerca
	 */
	public List<Ticket> findAllByNameContains(String name) {

		return repository.findByNameContains(name);

	}

	/**
	 * 
	 * @param status, filto di ricerca del ticket
	 * @return la lista dei tickets che contentono lo status della ricerca
	 */
	public List<Ticket> findAllByStatus(String status) {

		return repository.findByStatus(status);

	}

	/**
	 * 
	 * @param id, id del ticket
	 * @return tutte le informazioni del ticket specifico
	 */
	public Ticket getById(int id) {

		return repository.findById(id).get();

	}

	/**
	 * @param ticket, l'oggeto pizza che deve essere aggiornato
	 * @return il repository aggiornato
	 */
	public Ticket update(Ticket ticket) {

		return repository.save(ticket);

	}

	/**
	 * @param ticket, l'oggeto pizza che deve essere creato
	 * @return il repository aggiornato
	 */
	public Ticket create(Ticket ticket) {

		return repository.save(ticket);

	}

	/**
	 * @param id, l'id del ticket che deve essere elimianto
	 */
	public void delete(int id) {

		repository.deleteById(id);

	}

}
