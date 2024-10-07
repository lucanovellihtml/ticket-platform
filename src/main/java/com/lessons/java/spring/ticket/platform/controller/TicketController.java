package com.lessons.java.spring.ticket.platform.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lessons.java.spring.ticket.platform.model.Note;
import com.lessons.java.spring.ticket.platform.model.Ticket;
import com.lessons.java.spring.ticket.platform.service.TicketService;

import jakarta.validation.Valid;

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
	@GetMapping("/list-tickets")
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

	/**
	 * 
	 * @return la lista di tutte le note del ticket;
	 */
	@GetMapping("/show/{id}/list-notes-ticket")
	public String showNotesOfTicket(@PathVariable("id") int id, Model model) {

		// Lista delle note del ticket passata nel model
		List<Note> listNotes = service.getById(id).getNotes();

		model.addAttribute("listNotes", listNotes);

		model.addAttribute("ticket", service.getById(id));

		return "/tickets/show-notes-of-ticket";

	}

	/**
	 * 
	 * @return Il singolo ticket;
	 */
	@GetMapping("/show/{id}")
	public String showSingleTicket(@PathVariable("id") int id, Model model) {

		model.addAttribute("ticket", service.getById(id));

		return "/tickets/single-ticket";

	}

	/**
	 * Viene mostrato il form con i dati del ticket che si vuole modificare
	 * 
	 * @return i dati del ticket richiesto;
	 * 
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, Model model) {

		model.addAttribute("ticket", service.getById(id));

		String status = service.getById(id).getStatus();
		service.getById(id).setStatus("");

		model.addAttribute("status", status);

		return "/tickets/form-edit-ticket";

	}

	/**
	 * Memorizzazione della singola pizza modificata nel form
	 * 
	 * @return Se i dati sono sbagliati, viene riproposto il form da ricompilare
	 * @return Una volta salvati i dati, viene restituita la lista dei ticket
	 *         aggiornata
	 */
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("ticket") Ticket formTicket, BindingResult bindingResult, Model model) {

		// Controllo se i campi compilati sono sbagliati
		// Se ci sono errori ,viene restituita la pagina del form da ricompilare
		// In caso contrario, i dati vengon salvati sul db
		if (bindingResult.hasErrors()) {
			return "/tickets/form-edit-ticket";
		}

		service.update(formTicket);

		return "redirect:/tickets/list-tickets";

	}

	/**
	 * Creazione della nota relativa al ticket selezionato
	 * 
	 * @return form per la creazione della singola nota;
	 */
	@GetMapping("/show/{id}/note")
	public String createNote(@PathVariable("id") int id, Model model) {

		Note note = new Note();
		Ticket ticket = service.getById(id);

		// Aggiunta la data corrente al form per la creazione della nota
		model.addAttribute("currentDate", LocalDate.now());

		note.setTicket(ticket);

		model.addAttribute("note", note);

		return "/notes/form-create-note";

	}
	
	/**
	 * Elimina il ticket selezionato;
	 * @return la lista dei tickets aggiornata;
	 */
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id) {

		// Elimino i dati dal repository;
		service.delete(id);

		return "redirect:/tickets/list-tickets";

	}

}
