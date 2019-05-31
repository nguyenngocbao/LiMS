package com.fsoft.libms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fsoft.libms.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	public Category findById(long id);

}
