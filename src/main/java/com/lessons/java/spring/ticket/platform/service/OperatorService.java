package com.lessons.java.spring.ticket.platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lessons.java.spring.ticket.platform.model.Operator;
import com.lessons.java.spring.ticket.platform.repo.OperatorRepository;

@Service
public class OperatorService {

	@Autowired
	private OperatorRepository repository;

	/**
	 * 
	 * @return la lista dei operators presenti nel repository
	 */
	public List<Operator> findAllOperators() {

		return repository.findAll();

	}

	/**
	 * 
	 * @param id, id dell'operator
	 * @return tutte le informazioni dell'operator specifico
	 */
	public Operator getById(int id) {

		return repository.findById(id).get();

	}

	/**
	 * @param operator, l'oggetto operator che deve essere aggiornato
	 * @return il repository aggiornato
	 */
	public Operator update(Operator operator) {

		return repository.save(operator);

	}

}
