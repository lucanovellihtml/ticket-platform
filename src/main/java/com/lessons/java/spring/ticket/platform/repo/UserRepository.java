package com.lessons.java.spring.ticket.platform.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lessons.java.spring.ticket.platform.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public Optional<User> findByEmail(String email);
}
