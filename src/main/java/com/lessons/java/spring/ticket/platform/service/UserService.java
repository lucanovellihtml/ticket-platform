package com.lessons.java.spring.ticket.platform.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lessons.java.spring.ticket.platform.model.User;
import com.lessons.java.spring.ticket.platform.repo.UserRepository;

@Service
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
	 * @return la lista dei Users presenti nel repository con lo status a false(quindi sono gli operatos attivi per prendere il ticket)
	 */
	public List<User> findAllUsersStatusFalse(Boolean status) {
		
		return repository.findByStatus(status);

	}
	
	/**
	 * 
	 * @return la lista dei Users presenti nel repository con lo status non a null(quindi gli operators)
	 */
	public List<User> findAllUsersOperators(Boolean statusTrue, Boolean statusFalse) {
		
		List<User> listUserActive, listUserNotActive; 
		List<User> listUser = new ArrayList<User>();
		
		listUserActive = repository.findByStatus(statusFalse);
		listUserNotActive = repository.findByStatus(statusTrue);
		
		listUser.addAll(listUserActive);
		listUser.addAll(listUserNotActive);
		
		return listUser;

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
	
	/**
	 * @param User, l'oggetto User che deve essere create
	 * @return il repository aggiornato
	 */
	public User create(User User) {

		return repository.save(User);

	}
	
	/**
	 * @param id, id dello user da cancellare
	 * @return il repository aggiornato
	 */
	public void delete(int id) {

		repository.deleteById(id);

	}
}
