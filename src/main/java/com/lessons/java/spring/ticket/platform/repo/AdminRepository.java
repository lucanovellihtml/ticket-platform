package com.lessons.java.spring.ticket.platform.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lessons.java.spring.ticket.platform.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

}
