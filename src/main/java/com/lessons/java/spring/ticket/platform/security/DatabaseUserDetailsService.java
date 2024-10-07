package com.lessons.java.spring.ticket.platform.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lessons.java.spring.ticket.platform.model.User;
import com.lessons.java.spring.ticket.platform.repo.UserRepository;

/**
 * Classe Service con il metodo per sapere se sul DB è presente l'user che ha fatto la login
 */
@Service
public class DatabaseUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<User> user = userRepository.findByEmail(email);
		
		if(user.isPresent())
			return  new DatabaseUserDetails(user.get());
		else
			throw new UsernameNotFoundException("Username not found");
	}
	
	
}
