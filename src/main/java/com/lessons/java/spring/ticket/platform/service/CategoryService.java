package com.lessons.java.spring.ticket.platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lessons.java.spring.ticket.platform.model.Category;
import com.lessons.java.spring.ticket.platform.repo.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository repository;

	/**
	 * 
	 * @return la lista delle Categories presenti nel repository
	 */
	public List<Category> findAllCategories() {

		return repository.findAll();

	}

	/**
	 * 
	 * @param id, id della category
	 * @return tutte le informazioni della category specifica
	 */
	public Category getById(int id) {

		return repository.findById(id).get();

	}

	/**
	 * @param Category, l'oggetto Category che deve essere aggiornato
	 * @return il repository aggiornato
	 */
	public Category update(Category category) {

		return repository.save(category);

	}
	
	/**
	 * @param Category, l'oggetto Category che deve essere creato
	 * @return il repository aggiornato
	 */
	public Category create(Category category) {

		return repository.save(category);

	}
	
	/**
	 * @param id, id della category da cancellare
	 * @return il repository aggiornato
	 */
	public void delete(int id) {

		repository.deleteById(id);

	}
}
