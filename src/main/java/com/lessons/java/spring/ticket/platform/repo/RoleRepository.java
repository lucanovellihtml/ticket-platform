package com.lessons.java.spring.ticket.platform.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lessons.java.spring.ticket.platform.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	/**
	 * query custom per cercare la role tramite nome
	 */
	public Set<Role> findByName(String name);
	
}
