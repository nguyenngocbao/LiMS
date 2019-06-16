package com.fsoft.libms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fsoft.libms.model.RequestNewBook;
import com.fsoft.libms.model.User;

public interface RequestNewBookRepository extends JpaRepository<RequestNewBook, Long> {

	public Page<RequestNewBook> findAll(Pageable pageable);
	public Page<RequestNewBook> findByUser(User user, Pageable pageable);


}
