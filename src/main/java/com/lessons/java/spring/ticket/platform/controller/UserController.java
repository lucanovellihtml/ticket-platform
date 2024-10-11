package com.lessons.java.spring.ticket.platform.controller;

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

import com.lessons.java.spring.ticket.platform.model.Ticket;
import com.lessons.java.spring.ticket.platform.model.User;
import com.lessons.java.spring.ticket.platform.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService service;

	/**
	 * Viene mostrato il form con i dati dello user che si vuole modificare
	 * 
	 * @return i dati dello user richiesto;
	 * 
	 */
	@GetMapping("/user-profile/edit/{id}")
	public String edit(@PathVariable("id") int id, Model model, Authentication authentication) {

		// True se non ci sono ticket da completare
		// False se c'è almeno un ticket da completare
		Boolean checkStatus = true;

		// Controllo se nella lista esistono dei tickets in stato "Da fare" o "In
		// carico"
		// Appena viene trovato un ticket in questo stato, viene settato il flag a false
		for (Ticket ticket : service.getById(id).getTickets()) {

			if (ticket.getStatus().equals("Da fare") || ticket.getStatus().equals("In corso")) {
				checkStatus = false;
			}
		}

		for (User user : service.findAllUsers()) {
			if (authentication.getName().equals(user.getEmail()))
				model.addAttribute("usernameId", user.getId());
		}

		// Pulisco il campo password dall'algoritmo per visualizzarlo meglio nel model
		service.getById(id).setPassword(service.getById(id).getPassword().replace("{noop}", ""));

		model.addAttribute("user", service.getById(id));
		model.addAttribute("checkStatus", checkStatus);

		return "/users/form-edit-user";

	}

	/**
	 * Memorizzazione del singolo user modificata nel form
	 * 
	 * @return Se i dati sono sbagliati, viene riproposto il form da ricompilare
	 * @return Una volta salvati i dati, viene restituita la pagina di setting dello
	 *         user aggiornata
	 */
	@PostMapping("/user-profile/edit/{id}")
	public String update(@Valid @ModelAttribute("user") User formUser, BindingResult bindingResult, Model model) {

		// Sbianco la password altrimenti si crea una somma di stringa se il campo
		// password non viene modificato
		service.getById(formUser.getId()).setPassword("");

		// Passo come parametro nel post submit form, l'associazione con i ruoli
		formUser.setRoles(service.getById(formUser.getId()).getRoles());

		// Controllo se i campi compilati sono sbagliati
		// Se ci sono errori ,viene restituita la pagina del form da ricompilare
		// In caso contrario, i dati vengon salvati sul db
		if (bindingResult.hasErrors()) {
			return "/users/form-edit-user";
		}

		// Imposto dentro al metodo il metodo che deve aggiungere alla stringa della
		// password
		formUser.setPassword("{noop}" + formUser.getPassword());

		service.update(formUser);

		return "redirect:/tickets/list-tickets";

	}

}
