package com.fsoft.libms.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
	Long countByBookAndStatusIn(Book book,Collection<LoanStatus> ages);
	List<LoanBook> findByStatusInOrderByReserveDateDesc(List<LoanStatus> liStatus);
	Long countByBookAndUser(Book book, User user );
	Long countByUserAndStatusIn( User findByUsername, List<LoanStatus> liStatus);
	Long countByBookAndUserAndStatusIn(Book book, User findByUsername, List<LoanStatus> liStatus);
	Long countByBookAndUserNotAndStatusIn(Book book ,User username,List<LoanStatus> liStatusRe);
	List<LoanBook> findByBookAndUserNotAndStatusIn(Book book ,User username,List<LoanStatus> liStatusRe);
	@Query(value = "select s from LoanBook s")
	LoanBook getBook();

}
