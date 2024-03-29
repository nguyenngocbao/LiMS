package com.fsoft.libms.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fsoft.libms.model.Book;
import com.fsoft.libms.model.Category;

public interface BookRepository extends JpaRepository<Book, Long> {

	  public Book findByIsbn( String isbn );
	  
	  public Page<Book> findByCategory(Category category, Pageable pageable);
	  
	  public Book findById (long id);
	  
	  public Page<Book> findAll(Pageable pageable);
	  
	  public Page<Book> findByNameContaining(String name, Pageable pageable);
	  
	  public Page<Book> findByAuthorContaining(String author, Pageable pageable);
	  
	  public Page<Book> findByAuthorContainingAndCategory(String author, Category category, Pageable pageable);
	  
	  public Page<Book> findByNameContainingAndCategory(String name, Category category, Pageable pageable);
}
