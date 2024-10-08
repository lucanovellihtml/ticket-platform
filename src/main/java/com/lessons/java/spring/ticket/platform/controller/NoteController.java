package com.lessons.java.spring.ticket.platform.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lessons.java.spring.ticket.platform.model.Note;
import com.lessons.java.spring.ticket.platform.service.NoteService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/notes")
public class NoteController {

	@Autowired
	private NoteService service;

	/**
	 * Memorizzazione della nota creata nel form;
	 * 
	 * @return Se i dati sono sbagliati, viene riproposto il form da ricompilare
	 * @return Una volta salvati i dati, viene restituita la lista dei ticket
	 *         aggiornata
	 */
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("note") Note formNote, BindingResult bindingResult,
			RedirectAttributes attributes, Model model) {

		if (bindingResult.hasErrors()) {
			//Inserisco i dati nel model anche nella store perchè se si riaggiona la pagina, i campi vengono sbiancati
			model.addAttribute("currentDate", LocalDate.now());
			return "/notes/form-create-note";
		}

		// Aggiunto alla nota il tempo e la data di creazione
		formNote.setCreateAt(LocalDateTime.now());

		service.create(formNote);

		// attributes.addFlashAttribute("successMessageCreate", "Discount created...");

		return "redirect:/tickets/show/" + formNote.getTicket().getId();

	}
	
	/**
	 * Elimina la nota selezionata;
	 * @return la lista delle note aggiornate;
	 */
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id) {

		//Salvo il valore della nota prima che venga eliminata per poi fare la redirect corretta
		int idTicket = service.getById(id).getTicket().getId();
		
		// Elimino i dati dal repository;
		service.delete(id);

		return "redirect:/tickets/show/" + idTicket + "/list-notes-ticket";
	}
}
