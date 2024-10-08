package com.lessons.java.spring.ticket.platform.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lessons.java.spring.ticket.platform.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

	/**
	 * query custom per cercare il ticket tramite nome
	 */
	public List<Ticket> findByNameContains(String name);
	
	/**
	 * query custom per cercare il ticket tramite status
	 */
	public List<Ticket> findByStatus(String status);

}
