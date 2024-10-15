package com.lessons.java.spring.ticket.platform.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lessons.java.spring.ticket.platform.model.Ticket;
import com.lessons.java.spring.ticket.platform.model.User;
import com.lessons.java.spring.ticket.platform.service.RoleService;
import com.lessons.java.spring.ticket.platform.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private RoleService serviceRole;

	/**
	 * 
	 * @return la lista di tutti i users senza filtro;
	 * @return la lista dei users filtrati;
	 */
	@GetMapping("/list-users")
	public String index(Authentication authentication, Model model) {

		List<User> listUsers;

		// Inserisco le informazioni dello user autenticato
		model.addAttribute("username", authentication.getName());
		
		// Creo la lista degli user solo operators e non admin, l'admin ha lo status a null
		listUsers = service.findAllUsersOperators(true, false);

		// Inserisco i dati nella lista del model per utilizzarli nella pagina html;
		model.addAttribute("users", listUsers);

		return "/users/index";

	}

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
	public String update(@Valid @ModelAttribute("user") User formUser, BindingResult bindingResult, Model model,
			RedirectAttributes attributes) {

		// Passo come parametro nel post submit form l'associazione con i ruoli
		formUser.setRoles(service.getById(formUser.getId()).getRoles());

		// Controllo se i campi compilati sono sbagliati
		// Se ci sono errori ,viene restituita la pagina del form da ricompilare
		// In caso contrario, i dati vengon salvati sul db
		if (bindingResult.hasErrors()) {
			
			// Quando ricarica la pagina per l'errore, ricarico i dati dello user specifico
			model.addAttribute("user", service.getById(formUser.getId()));
			
			return "/users/form-edit-user";
		}

		// Imposto il metodo che deve aggiungere alla stringa della
		// password il tipo di crittografia
		formUser.setPassword("{noop}" + formUser.getPassword());

		service.update(formUser);

		attributes.addFlashAttribute("successMessageUpdate", "User updated...");

		return "redirect:/users/user-profile/edit/" + formUser.getId();

	}

	/**
	 * Creazione dello user
	 * 
	 * @return form per la creazione dello user;
	 */
	@GetMapping("/create")
	public String create(Model model, Authentication authentication) {

		// Logica per passare il parametro ,dello user autenticato , nel nav per
		// impostare il bottone setting
		for (User user : service.findAllUsers()) {
			if (authentication.getName().equals(user.getEmail()))
				model.addAttribute("usernameId", user.getId());
		}

		model.addAttribute("user", new User());

		return "/users/form-create-user";

	}

	/**
	 * Memorizza i dati del user passati dal form della create
	 * 
	 * @return se i dati della form sono sbagliati, restituisce il form della create
	 *         da ricompilare
	 * @return una volta salvato il user nuovo, viene restituita la lista dei
	 *         users
	 */
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("user") User formUser, BindingResult bindingResult, Model model,
			RedirectAttributes attributes) {

		// Controllo se i campi compilati sono errati
		if (bindingResult.hasErrors()) {
			return "/users/form-create-user";
		}

		// Imposto il metodo che deve aggiungere alla stringa della
		// password il tipo di crittografia
		formUser.setPassword("{noop}" + formUser.getPassword());

		// Aggiungo di default la role OPERATOR allo user appena creato
		formUser.setRoles(serviceRole.findByName("OPERATOR"));

		service.create(formUser);

		attributes.addFlashAttribute("successMessageCreate", "User created...");

		return "redirect:/users/list-users";

	}

	/**
	 * Elimina lo user selezionato;
	 * 
	 * @return la lista degli users aggiornati;
	 */
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id, RedirectAttributes attributes) {

		// Elimino i dati dal repository;
		service.delete(id);

		attributes.addFlashAttribute("successMessageDelete", "Operator deleted...");

		return "redirect:/users/list-users";

	}

}
