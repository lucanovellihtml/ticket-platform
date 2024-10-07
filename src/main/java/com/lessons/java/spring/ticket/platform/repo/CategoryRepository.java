package com.lessons.java.spring.ticket.platform.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lessons.java.spring.ticket.platform.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
