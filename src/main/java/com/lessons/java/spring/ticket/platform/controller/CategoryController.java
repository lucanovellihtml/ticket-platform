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

import com.lessons.java.spring.ticket.platform.model.Category;
import com.lessons.java.spring.ticket.platform.service.CategoryService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("categories")
public class CategoryController {

	@Autowired
	CategoryService service;

	/**
	 * 
	 * @return la lista di tutti le categories;
	 */
	@GetMapping("/list-categories")
	public String index(Model model, Authentication authentication) {

		List<Category> listCategories;

		// Inserisco le informazioni dello user autenticato
		model.addAttribute("username", authentication.getName());

		listCategories = service.findAllCategories();

		// Inserisco i dati nella lista del model per utilizzarli nella pagina html;
		model.addAttribute("categories", listCategories);

		return "/categories/index";

	}

	/**
	 * Viene mostrato il form con i dati della category che si vuole modificare
	 * 
	 * @return i dati dello category richiesta;
	 * 
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, Model model, Authentication authentication) {

		// Inserisco le informazioni dello user autenticato
		model.addAttribute("username", authentication.getName());

		// Inserisco i dati nella lista del model per utilizzarli nella pagina html;
		model.addAttribute("category", service.getById(id));

		return "/categories/form-edit-category";

	}

	/**
	 * Memorizzazione della category modificata nel form
	 * 
	 * @return Se i dati sono sbagliati, viene riproposto il form da ricompilare
	 * @return Una volta salvati i dati, viene restituita la lista delle
	 *         categories
	 */
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("category") Category formCategory, BindingResult bindingResult,
			Model model, RedirectAttributes attributes) {

		// Passo come parametro nel post submit form l'associazione con i ticket
		formCategory.setTickets(service.getById(formCategory.getId()).getTickets());

		// Controllo se i campi compilati sono sbagliati
		// Se ci sono errori ,viene restituita la pagina del form da ricompilare
		// In caso contrario, i dati vengon salvati sul db
		if (bindingResult.hasErrors()) {
			return "/categories/form-edit-category";
		}

		service.update(formCategory);

		attributes.addFlashAttribute("successMessageUpdate", "Category updated...");

		return "redirect:/categories/list-categories";
	}
	
	/**
	 * Creazione della category
	 * 
	 * @return form per la creazione della category
	 */
	@GetMapping("/create")
	public String create(Model model, Authentication authentication) {

		model.addAttribute("category", new Category());

		return "/categories/form-create-category";

	}

	/**
	 * Memorizza i dati della category passata dal form della create
	 * 
	 * @return se i dati della form sono sbagliati, restituisce il form della create
	 *         da ricompilare
	 * @return una volta salvata la category nuova, viene restituita la lista delle
	 *         categories
	 */
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("category") Category formCategory, BindingResult bindingResult, Model model,
			RedirectAttributes attributes) {

		// Controllo se i campi compilati sono errati
		if (bindingResult.hasErrors()) {
			return "/categories/form-create-category";
		}

		service.create(formCategory);

		attributes.addFlashAttribute("successMessageCreate", "Category created...");

		return "redirect:/categories/list-categories";

	}
	
	/**
	 * Elimina la category selezionato;
	 * 
	 * @return la lista delle categories aggiornata;
	 */
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id, RedirectAttributes attributes) {

		// Elimino i dati dal repository;
		service.delete(id);

		attributes.addFlashAttribute("successMessageDelete", "Category deleted...");

		return "redirect:/categories/list-categories";

	}
}
