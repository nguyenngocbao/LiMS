package com.fsoft.libms.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fsoft.libms.model.Book;
import com.fsoft.libms.model.LoanBook;
import com.fsoft.libms.model.LoanStatus;
import com.fsoft.libms.model.User;

public interface LoanBookRepository extends JpaRepository<LoanBook, Long>  {
	List<LoanBook> findByStatusIn(Collection<LoanStatus> ages);
	LoanBook findById (long id);
	List<LoanBook> findByBookAndStatusIn(Book book,Collection<LoanStatus> ages);
	List<LoanBook> findByUserAndStatusIn(User user,Collection<LoanStatus> ages);
	List<LoanBook> findByUserAndDisableAndStatusIn(User user,boolean disable,Collection<LoanStatus> ages);
	

}
