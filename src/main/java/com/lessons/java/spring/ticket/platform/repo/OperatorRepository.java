package com.lessons.java.spring.ticket.platform.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lessons.java.spring.ticket.platform.model.Operator;

public interface OperatorRepository extends JpaRepository<Operator, Integer> {

}
