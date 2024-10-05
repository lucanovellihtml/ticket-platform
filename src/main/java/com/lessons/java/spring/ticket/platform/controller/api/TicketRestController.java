package com.lessons.java.spring.ticket.platform.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lessons.java.spring.ticket.platform.model.Ticket;
import com.lessons.java.spring.ticket.platform.service.TicketService;

/**
 * Controller per la gestione delle Rest Api sul'entity Ticket
 * CrossOrigin permette di richiamare l'API anche non da locale
 */
@RestController
@CrossOrigin
@RequestMapping("api/tickets")
public class TicketRestController {

	@Autowired
	private TicketService service;
	
	/**
	 * Metodo che restituisce tutti i ticket presenti nel repository
	 * @return la lista dei tickets presenti nel repository
	 */
	@GetMapping
	public List<Ticket> index() {

		List<Ticket> listTickets;

		listTickets = service.findAllTickets();

		return listTickets;

	}
	
}
