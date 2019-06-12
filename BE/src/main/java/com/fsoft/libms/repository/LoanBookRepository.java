package com.fsoft.libms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fsoft.libms.model.LoanBook;

public interface LoanBookRepository extends JpaRepository<LoanBook, Long>  {

}
