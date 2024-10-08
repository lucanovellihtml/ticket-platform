package com.lessons.java.spring.ticket.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lessons.java.spring.ticket.platform.model.Note;
import com.lessons.java.spring.ticket.platform.repo.NoteRepository;

@Service
public class NoteService {

	@Autowired
	private NoteRepository repository;

	/**
	 * @param note, oggetto note da creare nel repository;
	 * @return la nota creata e salvata;
	 */
	public Note create(Note note) {

		return repository.save(note);

	}
	
	/**
	 * 
	 * @param id, id della nota
	 * @return tutte le informazioni della nota specifica
	 */
	public Note getById(int id) {

		return repository.findById(id).get();

	}
	
	/**
	 * @param id, l'id della nota che deve essere eliminata
	 */
	public void delete(int id) {

		repository.deleteById(id);
		
	}
	

}
