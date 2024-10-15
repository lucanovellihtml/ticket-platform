package com.lessons.java.spring.ticket.platform.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lessons.java.spring.ticket.platform.model.Note;
import com.lessons.java.spring.ticket.platform.model.Ticket;
import com.lessons.java.spring.ticket.platform.model.User;
import com.lessons.java.spring.ticket.platform.service.CategoryService;
import com.lessons.java.spring.ticket.platform.service.TicketService;
import com.lessons.java.spring.ticket.platform.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/tickets")
public class TicketController {

	@Autowired
	private TicketService service;

	// Service per le categories
	@Autowired
	private CategoryService serviceCategory;

	// Service per gli users
	@Autowired
	private UserService serviceUser;

	/**
	 * 
	 * @return la lista di tutti i tickets senza filtro;
	 * @return la lista dei tickets filtrati;
	 */
	@GetMapping("/list-tickets")
	public String index(@RequestParam(name = "name", required = false) String name, Authentication authentication,
			Model model) {

		// Prendo i dati da mostrare a "/tickets/index":
		List<Ticket> listTickets;

		
		// Inserisco le informazioni dello user autenticato
		model.addAttribute("username", authentication.getName());
		
		// Logica per passare il parametro ,dello user autenticato , nel nav per impostare il bottone setting
		for(User user : serviceUser.findAllUsers()) {
			if(authentication.getName().equals(user.getEmail()))
				model.addAttribute("usernameId", user.getId());
		}
		

		if (name != null && !name.isEmpty())
			listTickets = service.findAllByNameContains(name);
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
	public String showNotesOfTicket(@PathVariable("id") int id, Model model, Authentication authentication) {

		// Logica per passare il parametro ,dello user autenticato , nel nav per impostare il bottone setting
		for(User user : serviceUser.findAllUsers()) {
			if(authentication.getName().equals(user.getEmail()))
				model.addAttribute("usernameId", user.getId());
		}
		
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
	public String showSingleTicket(@PathVariable("id") int id, Model model, Authentication authentication) {

		// Logica per passare il parametro ,dello user autenticato , nel nav per impostare il bottone setting
		for(User user : serviceUser.findAllUsers()) {
			if(authentication.getName().equals(user.getEmail()))
				model.addAttribute("usernameId", user.getId());
		}
		
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
	public String edit(@PathVariable("id") int id, Model model, Authentication authentication) {

		// Logica per passare il parametro ,dello user autenticato , nel nav per impostare il bottone setting
		for(User user : serviceUser.findAllUsers()) {
			if(authentication.getName().equals(user.getEmail()))
				model.addAttribute("usernameId", user.getId());
		}

		
		model.addAttribute("ticket", service.getById(id));
		
		// Mostro l'email dello user che ha in carico il ticket
		model.addAttribute("userEmail", service.getById(id).getUser().getEmail());
		
		// Mostro la categoria presente del ticket
		model.addAttribute("categoryTitle", service.getById(id).getCategory().getTitle());
		
		// Mostro tutte le categorie disponibili per farle scegliere all'admin
		model.addAttribute("categories", serviceCategory.findAllCategories());
		
		// Mostro tutti gli user disponibili per farle scegliere all'admin
		model.addAttribute("users", serviceUser.findAllUsersStatusFalse(false));
		
		// Sbianco lo status altrimenti si sovrascrivono, essendo stringe, ad ogni update
		String status = service.getById(id).getStatus();
		service.getById(id).setStatus("");
		model.addAttribute("status", status);

		return "/tickets/form-edit-ticket";

	}

	/**
	 * Memorizzazione della singola ticket modificata nel form
	 * 
	 * @return Se i dati sono sbagliati, viene riproposto il form da ricompilare
	 * @return Una volta salvati i dati, viene restituita la lista dei ticket
	 *         aggiornata
	 */
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("ticket") Ticket formTicket, BindingResult bindingResult, Model model, RedirectAttributes attributes) {

		// Controllo se i campi compilati sono sbagliati
		// Se ci sono errori ,viene restituita la pagina del form da ricompilare
		// In caso contrario, i dati vengon salvati sul db
		if (bindingResult.hasErrors()) {
			
			// Se ricarica inserisco le categories presenti nel repository
			model.addAttribute("categories", serviceCategory.findAllCategories());
			
			// Se ricarcia isnerisco gli user presenti nel repository
			model.addAttribute("users", serviceUser.findAllUsersStatusFalse(false));
			
			// Se ricarica inserisco l'email dello user associato al ticket di modifica
			model.addAttribute("userEmail", service.getById(formTicket.getId()).getUser().getEmail());
			
			// Se ricarica inserisco il nome della category dello user associato al ticket di modifica
			model.addAttribute("categoryTitle", service.getById(formTicket.getId()).getCategory().getTitle());
			
			// Se ricarica la pagina viene perso il dato dell status, in questo modo non vine perso
			String status = service.getById(formTicket.getId()).getStatus();
			service.getById(formTicket.getId()).setStatus("");
			model.addAttribute("status", status);

			System.out.println(bindingResult.getAllErrors());
			
			return "/tickets/form-edit-ticket";
		}

		
		service.update(formTicket);
		
		attributes.addFlashAttribute("successMessageUpdate", "Ticket updated...");

		return "redirect:/tickets/list-tickets";

	}

	/**
	 * Creazione della nota relativa al ticket selezionato
	 * 
	 * @return form per la creazione della singola nota;
	 */
	@GetMapping("/show/{id}/note")
	public String createNote(@PathVariable("id") int id, Model model, Authentication authentication) {
		
		// Logica per passare il parametro ,dello user autenticato , nel nav per impostare il bottone setting
		for(User user : serviceUser.findAllUsers()) {
			if(authentication.getName().equals(user.getEmail()))
				model.addAttribute("usernameId", user.getId());
		}
		
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
	 * 
	 * @return la lista dei tickets aggiornata;
	 */
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id, RedirectAttributes attributes) {

		// Elimino i dati dal repository;
		service.delete(id);

		attributes.addFlashAttribute("successMessageDelete", "Ticket deleted...");
		
		return "redirect:/tickets/list-tickets";

	}

	/**
	 * Metodo per la creazione del ticket
	 * 
	 * @return restituisce il form per la creazione del ticket;
	 */
	@GetMapping("/create")
	public String create(Model model, Authentication authentication) {

		// Logica per passare il parametro ,dello user autenticato , nel nav per impostare il bottone setting
		for(User user : serviceUser.findAllUsers()) {
			if(authentication.getName().equals(user.getEmail()))
				model.addAttribute("usernameId", user.getId());
		}
		
		// Inserisco l'oggetto ticket vuoto per permettere di richiamare sempre la
		// pagina senza dati
		model.addAttribute("ticket", new Ticket());

		// Mostro l'elenco delle categories disponibili;
		model.addAttribute("categories", serviceCategory.findAllCategories());

		// Mostro l'elenco degli users disponibili con lo stato a false;
		model.addAttribute("users", serviceUser.findAllUsersStatusFalse(false));

		return "/tickets/form-create-ticket";

	}

	/**
	 * Memorizza i dati del ticket passati dal form della create
	 * 
	 * @return se i dati della form sono sbagliati, restituisce il form della create
	 *         da ricompilare
	 * @return una volta salvato il ticket nuovo, viene restituita la lista dei
	 *         tickets
	 */
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("ticket") Ticket formTicket, BindingResult bindingResult, Model model, RedirectAttributes attributes) {

		// Controllo se i campi compilati sono errati
		if (bindingResult.hasErrors()) {
			// Inserisco i dati nel model anche nella store perchè se si riaggiona la
			// pagina, i campi vengono sbiancati
			model.addAttribute("categories", serviceCategory.findAllCategories());
			model.addAttribute("users", serviceUser.findAllUsersStatusFalse(false));
			return "/tickets/form-create-ticket";
		}

		service.create(formTicket);
		
		attributes.addFlashAttribute("successMessageCreate", "Ticket created...");

		return "redirect:/tickets/list-tickets";

	}

}
