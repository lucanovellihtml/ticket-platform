package com.lessons.java.spring.ticket.platform.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lessons.java.spring.ticket.platform.model.User;
import com.lessons.java.spring.ticket.platform.repo.UserRepository;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		// Cerca nel database se esiste un utente che abbia lo username richiesto
		Optional<User> user = userRepository.findByEmail(email);

		// Se l'username è presente, allora costruiscti una nuova istanza con l'utente
		// esistente
		if (user.isPresent()) {
			return new DatabaseUserDetails(user.get());
		} else {
			throw new UsernameNotFoundException("Username not found");
		}
	}

}
