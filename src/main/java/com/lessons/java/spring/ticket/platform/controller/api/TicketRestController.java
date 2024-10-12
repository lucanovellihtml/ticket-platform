package com.lessons.java.spring.ticket.platform.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lessons.java.spring.ticket.platform.model.Ticket;
import com.lessons.java.spring.ticket.platform.service.TicketService;

/**
 * Controller per la gestione delle Rest Api sul'entity Ticket CrossOrigin
 * permette di richiamare l'API anche non da locale
 */
@RestController
@CrossOrigin
@RequestMapping("api/tickets")
public class TicketRestController {

	@Autowired
	private TicketService service;

//	@Autowired
//	private CategoryService serviceCategory;

	/**
	 * Metodo che restituisce tutti i ticket presenti nel repository
	 * 
	 * @return la Lista dei tickets presenti nel repository
	 */
	@GetMapping("List-tickets")
	public ResponseEntity<List<Ticket>> index() {

		List<Ticket> listTickets;

		listTickets = service.findAllTickets();

		if (!listTickets.isEmpty())
			// Controllo se la lista non è vuote
			// Se super la condizione, restituisco un codice HTTP 200 con i dati
			return new ResponseEntity<List<Ticket>>(listTickets, HttpStatus.OK);

		// Se non entra nell'if, di default viene restitutito un codice HTTP 404
		return new ResponseEntity<List<Ticket>>(HttpStatus.NOT_FOUND);

	}

	/**
	 * Metodo che restituisce tutti i ticket presenti nel repository con il filtro
	 * status
	 * 
	 * @return la Lista dei tickets presenti nel repository con il filtro status
	 */
	@GetMapping("status-filter")
	public ResponseEntity<List<Ticket>> indexStatus(@RequestParam(name = "status", required = true) String status) {

		List<Ticket> listTickets;

		if (status != null && !status.isEmpty()) {
			listTickets = service.findAllByStatus(status);

			// Controllo se la lista non è vuote
			// Se super la condizione, restituisco un codice HTTP 200 con i dati
			if (!listTickets.isEmpty())
				return new ResponseEntity<List<Ticket>>(listTickets, HttpStatus.OK);
		}

		// Se non entra nell'if, di default viene restitutito un codice HTTP 404
		return new ResponseEntity<List<Ticket>>(HttpStatus.NOT_FOUND);

	}

	/**
	 * Metodo che restituisce tutti i ticket presenti nel repository con il filtro
	 * category
	 * 
	 * @return la Lista dei tickets presenti nel repository con il filtro category
	 */
	@GetMapping("category-filter")
	public ResponseEntity<List<Ticket>> indexCategory(
			@RequestParam(name = "category", required = true) String category) {

		List<Ticket> listTickets;

		if (category != null && !category.isEmpty()) {
			listTickets = service.findAllByCategory(category);

			// Controllo se la lista non è vuote
			// Se super la condinzione, restituisco un codice HTTP 200 con i dati
			if (!listTickets.isEmpty())
				return new ResponseEntity<List<Ticket>>(listTickets, HttpStatus.OK);
		}

		// Se non entra nell'if, di default viene restitutito un codice HTTP 404
		return new ResponseEntity<List<Ticket>>(HttpStatus.NOT_FOUND);

	}

}
