package com.fsoft.libms.service;

import java.util.List;

import com.fsoft.libms.exception.LibMsException;
import com.fsoft.libms.model.LoanBook;

public interface ILoanBookService {
	/**
	 * loan book
	 * 
	 */
	public void loanBook(long id) throws LibMsException;

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

}
