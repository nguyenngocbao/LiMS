package com.fsoft.libms.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fsoft.libms.exception.LibMsException;
import com.fsoft.libms.model.Book;
import com.fsoft.libms.model.CodeId;
import com.fsoft.libms.model.LoanBook;
import com.fsoft.libms.model.LoanStatus;
import com.fsoft.libms.model.User;
import com.fsoft.libms.repository.BookRepository;
import com.fsoft.libms.repository.LoanBookRepository;
import com.fsoft.libms.repository.UserRepository;
import com.fsoft.libms.security.token.TokenProvider;
import com.fsoft.libms.service.AbstractService;
import com.fsoft.libms.service.ILoanBookService;

@Service
@Transactional(readOnly = true)
public class LoanBookService extends AbstractService implements ILoanBookService {
	@Autowired
	private LoanBookRepository loanBookRepo;
	@Autowired
	private BookRepository bookRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	TokenProvider tokenProvider;

	@Override
	@Transactional
	public LoanBook loanBook(long id) throws LibMsException {
		LoanBook request = new LoanBook();
		request.setBook(bookRepo.findById(id));
		request.setRequestDate(getTime());
		request.setCode(new CodeId());
		request.setStatus(LoanStatus.WAITING);
		request.setUser(userRepo.findByUsername(tokenProvider.getAuthToken().getName()));
		return loanBookRepo.saveAndFlush(request);

	}

	@Override
	public void cancelLoan(long id) throws LibMsException {
		// TODO Auto-generated method stub

	}
	@Transactional
	@Override
	public void returnBook(long id) throws LibMsException {
		LoanBook loan = loanBookRepo.findById(id);
		loan.setStatus(LoanStatus.RETURNING);
		loanBookRepo.saveAndFlush(loan);

	}

	@Override
	public List<LoanBook> getWaiting() throws LibMsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LoanBook> getLoaning() throws LibMsException {
		// TODO Auto-generated method stub
		return null;
	}
	@Transactional
	@Override
	public void reserveBook(long id) throws LibMsException {
		LoanBook request = new LoanBook();
		request.setBook(bookRepo.findById(id));
		request.setReserveDate(getTime());
		request.setCode(new CodeId());
		request.setStatus(LoanStatus.RESERVE);
		request.setUser(userRepo.findByUsername(tokenProvider.getAuthToken().getName()));
		loanBookRepo.saveAndFlush(request);

	}

	@Override
	public List<LoanBook> getReserve() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getTime() {
		return System.currentTimeMillis();
	}

	@Override
	public List<LoanBook> getAll() throws LibMsException {

		return loanBookRepo.findAll();
	}

	@Override
	public List<LoanBook> getRequest() {
		List<LoanStatus> liStatus = Arrays.asList(LoanStatus.WAITING, LoanStatus.LOANING_ACCEPT,
				LoanStatus.LOANING_REJECT);
		User user = userRepo.findByUsername(tokenProvider.getAuthToken().getName());
		return loanBookRepo.findByUserAndStatusIn(user,liStatus);
	}

	@Transactional
	@Override
	public void deleteRequest(Long id) {
		loanBookRepo.delete(id);

	}

	@Override
	public List<LoanBook> getRequestByBook(Long id) {
		List<LoanStatus> liStatus = Arrays.asList(LoanStatus.WAITING, 
				LoanStatus.LOANING_ACCEPT, LoanStatus.LOANING);
		Book book = bookRepo.findById(id);
		return loanBookRepo.findByBookAndStatusIn(book, liStatus);
	}

	@Override
	public List<LoanBook> getRequestByUser(Long id) {
		List<LoanStatus> liStatus = Arrays.asList(LoanStatus.WAITING, 
				LoanStatus.RESERVE, LoanStatus.LOANING);
		User user = userRepo.findOne(id);
		return loanBookRepo.findByUserAndStatusIn(user, liStatus);
	}
	@Transactional
	@Override
	public void acceptLoaning(long id) {
		LoanBook loan = loanBookRepo.findById(id);
		loan.setStatus(LoanStatus.LOANING_ACCEPT);
		loanBookRepo.saveAndFlush(loan);
		
		
	}
	@Transactional
	@Override
	public void confirmLoaning(long id) {
		LoanBook loan = loanBookRepo.findById(id);
		loan.setStatus(LoanStatus.LOANING);
		loan.setLoanDate(getTime());
		loanBookRepo.saveAndFlush(loan);
		
		
	}
	@Transactional
	@Override
	public void confirmReturn(long id) {
		LoanBook loan = loanBookRepo.findById(id);
		loan.setStatus(LoanStatus.RETURNED);
		loan.setReturnDate(getTime());
		loanBookRepo.saveAndFlush(loan);
		
		
	}
	@Transactional
	@Override
	public void rejectLoaning(long id, String reason) {
		LoanBook loan = loanBookRepo.findById(id);
		loan.setStatus(LoanStatus.LOANING_REJECT);
		loan.setReason(reason);
		loanBookRepo.saveAndFlush(loan);
	}

	@Override
	public List<LoanBook> getRequestOnly() {
		List<LoanStatus> liStatus = Arrays.asList(LoanStatus.WAITING);
		return loanBookRepo.findByStatusIn(liStatus);
	}
	@Override
	public List<LoanBook> getRequestAccept() {
		List<LoanStatus> liStatus = Arrays.asList(LoanStatus.LOANING_ACCEPT);
		return loanBookRepo.findByStatusIn(liStatus);
	}
	
	@Override
	public List<LoanBook> getRequestLoaing() {
		List<LoanStatus> liStatus = Arrays.asList(LoanStatus.LOANING);
		User user = userRepo.findByUsername(tokenProvider.getAuthToken().getName());
		return loanBookRepo.findByUserAndDisableAndStatusIn(user,false,liStatus);
	}
	@Transactional
	@Override
	public void disable(long id) {
		LoanBook loan = loanBookRepo.findById(id);
		
		loan.setDisable(true);
		loanBookRepo.saveAndFlush(loan);
		
	}

	@Override
	public List<LoanBook> getRequestReturning() {
		List<LoanStatus> liStatus = Arrays.asList(LoanStatus.RETURNING);
		return loanBookRepo.findByStatusIn(liStatus);
	}
	

}
