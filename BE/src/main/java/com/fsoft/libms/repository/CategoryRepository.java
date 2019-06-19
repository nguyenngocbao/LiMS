package com.fsoft.libms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fsoft.libms.model.Category;
import com.fsoft.libms.model.LoanBook;
import com.fsoft.libms.model.LoanStatus;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	public Category findById(long id);
	public Category findByName(String name);
	
	@Query("select e from Category e where e.id< 10")
	List<Category> getBookLoaning();

}
