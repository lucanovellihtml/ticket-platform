package com.lessons.java.spring.ticket.platform.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lessons.java.spring.ticket.platform.model.Role;
import com.lessons.java.spring.ticket.platform.repo.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository repository;
	
	/**
	 * @return la lista della role con il filtro del nome
	 */
	public Set<Role> findByName(String name) {

		return repository.findByName(name);

	}
	
}
