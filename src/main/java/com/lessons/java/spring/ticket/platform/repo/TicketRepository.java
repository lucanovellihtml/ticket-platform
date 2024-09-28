package com.lessons.java.spring.ticket.platform.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lessons.java.spring.ticket.platform.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

	/**
	 * query custom per cercare il ticket tramite nome
	 */
	public List<Ticket> findByName(String name);
}
