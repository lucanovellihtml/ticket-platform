package com.lessons.java.spring.ticket.platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lessons.java.spring.ticket.platform.model.User;
import com.lessons.java.spring.ticket.platform.repo.UserRepository;

public class UserService {

	@Autowired
	private UserRepository repository;
	
	/**
	 * 
	 * @return la lista dei Users presenti nel repository
	 */
	public List<User> findAllUsers() {

		return repository.findAll();

	}

	/**
	 * 
	 * @param id, id dell'User
	 * @return tutte le informazioni dell'User specifico
	 */
	public User getById(int id) {

		return repository.findById(id).get();

	}

	/**
	 * @param User, l'oggetto User che deve essere aggiornato
	 * @return il repository aggiornato
	 */
	public User update(User User) {

		return repository.save(User);

	}
}
