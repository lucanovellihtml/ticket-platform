package com.lessons.java.spring.ticket.platform.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Classe di configurazione per il sistema di sicurezza di autenticazione e autorizzazione
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	/*
	 * Aggiunto il csrf().disable() per accede a una risorsa senza autorizzazione
	 */
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests().requestMatchers("/tickets/**", "/notes/**").hasAuthority("ADMIN")
				.requestMatchers("/notes/**").hasAuthority("OPERATOR")
				.requestMatchers("/**").permitAll()
				.and().formLogin()
				.and().logout()
				.and().exceptionHandling()
				.and().csrf().disable();
		return http.build();
	}

	/**
	 * il metodo delega la procedura di codifica della password al database
	 * 
	 * @return la password codificata
	 */
	@Bean
	DatabaseUserDetailsService userDetailsService() {
		return new DatabaseUserDetailsService();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	/**
	 * Metodo per gestire l'autenticazione su database
	 * @return l'oggetto provider che mette insieme i le informazioni dell'user passato nel form login con la relativa password,
	 * per eseguire la query per autenticare l'user
	 */
	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

}
