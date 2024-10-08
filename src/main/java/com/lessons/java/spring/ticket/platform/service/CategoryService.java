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
}
