package com.fsoft.libms.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fsoft.libms.exception.LibMsException;
import com.fsoft.libms.model.Book;
import com.fsoft.libms.model.BookStatus;
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
		if(validateLoanBook(id)) {
			LoanBook request = new LoanBook();
			request.setBook(bookRepo.findById(id));
			request.setRequestDate(getTime());
			request.setCode(new CodeId());
			request.setStatus(LoanStatus.WAITING);
			request.setUser(userRepo.findByUsername(tokenProvider.getAuthToken().getName()));
			return loanBookRepo.saveAndFlush(request);
		}else {
			throw new LibMsException("");
		}
		
		
	}

	private boolean validateLoanBook(long id) throws LibMsException {
		if (!bookRepo.exists(id)) {
			throw new LibMsException("Sách không tồn tại");
		}
		List<LoanStatus> liStatus = Arrays.asList(LoanStatus.WAITING, LoanStatus.LOANING_ACCEPT,
				LoanStatus.LOANING);
		Book book = bookRepo.findById(id);
			boolean available = loanBookRepo.countByBookAndStatusIn(book, liStatus) < book.getQuantity();
		if (!available) {
			throw new LibMsException("Sách đã bị mượn hết");
		}
		if(loanBookRepo.countByBookAndUserAndStatusIn( book,userRepo.findByUsername(tokenProvider.getAuthToken().getName()),liStatus) > 0) {
			throw new LibMsException("Bạn đã đang yêu cầu hoặc mượn quyển sách này rồi");
		}
		if(loanBookRepo.countByUserAndStatusIn( userRepo.findByUsername(tokenProvider.getAuthToken().getName()),liStatus) >= 3) {
			throw new LibMsException("Bạn đã mượn quá 3 quyển sách");
		}
		return true;
		
		
		
		
	}

	@Override
	public void cancelLoan(long id) throws LibMsException {
		// TODO Auto-generated method stub

	}
	@Transactional
	@Override
	public void returnBook(long id) throws LibMsException {
		if(loanBookRepo.exists(id)) {
			LoanBook loan = loanBookRepo.findById(id);
			if(loan.getStatus().equals(LoanStatus.LOANING)) {
				loan.setStatus(LoanStatus.RETURNING);
				loanBookRepo.saveAndFlush(loan);
			}else {
				throw new LibMsException("ban dang khong muon quyen sach nay");
			}	
		}else {
			throw new LibMsException("khong co yeu cau");
		}
		

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
		if(loanBookRepo.exists(id)) { 
			Book book = bookRepo.findById(id);
			validateReserve(book);
			LoanBook request = new LoanBook();
			request.setBook(book);
			request.setReserveDate(getTime());
			request.setCode(new CodeId());
			request.setStatus(LoanStatus.RESERVE);
			request.setUser(userRepo.findByUsername(tokenProvider.getAuthToken().getName()));
			loanBookRepo.saveAndFlush(request);
		}else {
			throw new LibMsException("khong ton tai");
		}
		

	}
	private boolean validateReserve(Book book) throws LibMsException{
		List<LoanStatus> liStatus = Arrays.asList(LoanStatus.WAITING, LoanStatus.LOANING_ACCEPT,
				LoanStatus.LOANING,LoanStatus.RESERVE);
			boolean available = loanBookRepo.countByBookAndStatusIn(book, liStatus) < book.getQuantity();
		if (available) {
			throw new LibMsException("Sách có thể mượn được");
		}
		if(loanBookRepo.countByBookAndUserAndStatusIn( book,userRepo.findByUsername(tokenProvider.getAuthToken().getName()),liStatus) > 0) {
			throw new LibMsException("Bạn đã đang yêu cầu hoặc mượn quyển sách này rồi");
		}
		if(loanBookRepo.countByUserAndStatusIn( userRepo.findByUsername(tokenProvider.getAuthToken().getName()),liStatus) >= 3) {
			throw new LibMsException("Bạn đã mượn quá 3 quyển sách");
		}
		List<LoanStatus> liStatusRe = Arrays.asList(LoanStatus.RESERVE);
		boolean availableRe = loanBookRepo.countByBookAndUserNotAndStatusIn(book,userRepo.findByUsername(tokenProvider.getAuthToken().getName()), liStatusRe) >= book.getQuantity();
		System.out.println(loanBookRepo.findByBookAndUserNotAndStatusIn(book,userRepo.findByUsername(tokenProvider.getAuthToken().getName()), liStatusRe));
		if (availableRe) {
			throw new LibMsException("Sách vượt quá số lượng cho đặt trước");
		}
		return true;
		
		
	}

	@Override
	public List<LoanBook> getReserve() {
		List<LoanStatus> liStatus = Arrays.asList(LoanStatus.RESERVE);
		User user = userRepo.findByUsername(tokenProvider.getAuthToken().getName());
		return loanBookRepo.findByUserAndDisableAndStatusIn(user,false, liStatus);
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
		return loanBookRepo.findByUserAndDisableAndStatusIn(user,false, liStatus);
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
		autoChangeReserveBook(loan.getBook());
		
		
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

	@Override
	public List<LoanBook> loaningBook() {
		
		return null;
	}
	private void autoChangeReserveBook(Book book) {
		List<LoanStatus> liStatus = Arrays.asList(LoanStatus.RESERVE);
		List<LoanBook> liLoans = loanBookRepo.findByStatusInOrderByReserveDateDesc(liStatus);
		if(liLoans.size() > 0) {
			LoanBook liLoan = liLoans.get(0);
			liLoan.setStatus(LoanStatus.WAITING);
			liLoan.setRequestDate(getTime());
			loanBookRepo.saveAndFlush(liLoan);
		}
		
		
	}
	

}
