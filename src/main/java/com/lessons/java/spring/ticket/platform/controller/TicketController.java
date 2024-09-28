package com.lessons.java.spring.ticket.platform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lessons.java.spring.ticket.platform.model.Ticket;
import com.lessons.java.spring.ticket.platform.service.TicketService;

@Controller
@RequestMapping("/tickets")
public class TicketController {

	@Autowired
	private TicketService service;

	/**
	 * 
	 * @return la lista di tutti i tickets senza filtro;
	 * @return la lista dei tickets filtrati;
	 */
	@GetMapping
	public String index(@RequestParam(name = "name", required = false) String name, Model model) {

		// Prendo i dati da mostrare a "/tickets/index":
		List<Ticket> listTickets;

		if (name != null && !name.isEmpty())
			listTickets = service.findAllByName(name);
		else
			listTickets = service.findAllTickets();

		// Inserisco i dati nella lista del model per utilizzarli nella pagina html;
		model.addAttribute("tickets", listTickets);

		return "/tickets/index";

	}

}
