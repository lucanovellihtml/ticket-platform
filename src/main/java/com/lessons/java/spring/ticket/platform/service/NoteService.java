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

}
