package com.fsoft.libms.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fsoft.libms.exception.LibMsException;
import com.fsoft.libms.model.LoanBook;

public interface ILoanBookService {
	/**
	 * loan book
	 * 
	 */
	public List<LoanBook> getAll() throws LibMsException;
	/**
	 * loan book
	 * 
	 */
	public LoanBook loanBook(long id) throws LibMsException;

	/**
	 * cancel loan
	 * 
	 */
	void cancelLoan(long id) throws LibMsException;

	/**
	 * return book
	 */
	public void returnBook(long id) throws LibMsException;

	/**
	 * get all the books that user is waiting to loan
	 */
	public List<LoanBook> getWaiting() throws LibMsException;

	/**
	 * get all the books that user is loaning
	 */
	public List<LoanBook> getLoaning() throws LibMsException;

	/**
	 * reserve book
	 */
	public void reserveBook(long id) throws LibMsException;

	/**
	 * get all the books that user reserve
	 */
	public List<LoanBook> getReserve();
	public List<LoanBook> getRequest();
	public void deleteRequest(Long id);
	public List<LoanBook> getRequestByBook(Long id);
	public List<LoanBook> getRequestByUser(Long id);
	public void acceptLoaning(long id);
	public void rejectLoaning(long id, String reason);
	void confirmReturn(long id);
	void confirmLoaning(long id);
	public Page<LoanBook> getRequestOnly(Pageable page);
	Page<LoanBook> getRequestAccept(Pageable page);
	List<LoanBook> getRequestLoaing();
	public void disable(long id);
	public List<LoanBook> getRequestReturning();
	public List<LoanBook> loaningBook();
	public Page<LoanBook> history(Pageable pageable);
	public Page<LoanBook> getRequestAccept(String search, String filter, Pageable page);
	

}
