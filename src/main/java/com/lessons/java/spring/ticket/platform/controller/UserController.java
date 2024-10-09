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

		for(User user : service.findAllUsers()) {
			if(authentication.getName().equals(user.getEmail()))
				model.addAttribute("usernameId", user.getId());
		}
		model.addAttribute("user", service.getById(id));

		return "/users/form-edit-user";

	}

	/**
	 * Memorizzazione del singolo user modificata nel form
	 * 
	 * @return Se i dati sono sbagliati, viene riproposto il form da ricompilare
	 * @return Una volta salvati i dati, viene restituita la pagina di setting dello user
	 *         aggiornata
	 */
	@PostMapping("/user-profile/edit/{id}")
	public String update(@Valid @ModelAttribute("user") User formUser, BindingResult bindingResult, Model model) {

		// Controllo se i campi compilati sono sbagliati
		// Se ci sono errori ,viene restituita la pagina del form da ricompilare
		// In caso contrario, i dati vengon salvati sul db
		if (bindingResult.hasErrors()) {
			return "/users/form-edit-user";
		}

		// Imposto dentro al metodo il metodo che deve aggiungere alla stringa della password
		formUser.setPassword("{noop}" + formUser.getPassword());
		
		service.update(formUser);

		return "redirect:/users/user-profile/edit/" + formUser.getId();

	}
	
}
