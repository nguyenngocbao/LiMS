package com.fsoft.libms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fsoft.libms.exception.LibMsException;
import com.fsoft.libms.model.LoanBook;
import com.fsoft.libms.service.ILoanBookService;
import com.fsoft.libms.uitl.LimsConverter;

@RestController
@RequestMapping("/api/loan")
public class LoanBookController {
	@Autowired
	private ILoanBookService loanBookService;
	@Autowired
	private LimsConverter conveter;

	/**
	 * api /reserve to reserve Book
	 * 
	 * @throws LibraryManagementException
	 * 
	 */

	@PostMapping("/reserve")
	public void reserveBook(@RequestBody String books) throws LibMsException {
		loanBookService.reserveBook(conveter.convertToId(books));

	}
	@GetMapping("/reserve")
	public List<LoanBook> getReserve() throws LibMsException {
		return loanBookService.getReserve();
	}
	@DeleteMapping("/delete/{id}")
	public void deleteRequest(@PathVariable("id") Long id) throws LibMsException {
		loanBookService.deleteRequest(id);

	}
	

	@GetMapping("/all")
	public List<LoanBook> get() throws LibMsException {
		return loanBookService.getAll();
	}

	@GetMapping("/requestOnly")
	public Page<LoanBook> getRequestOnly(Pageable page) throws LibMsException {
		return loanBookService.getRequestOnly(page);
	}
	@GetMapping("/requestAccept")
	public Page<LoanBook> getRequestAccept(
			@RequestParam(value="search", required=false) String search,
			@RequestParam(value="filter", required=false) String filter,Pageable page) throws LibMsException {
		return loanBookService.getRequestAccept(search,filter,page);
	}
	@GetMapping("/requestLoaning")
	public List<LoanBook> getRequestLoaning() throws LibMsException {
		return loanBookService.getRequestLoaing();
	}
	@GetMapping("/requestReturning")
	public List<LoanBook> getRequestReturning() throws LibMsException {
		return loanBookService.getRequestReturning();
	}
	
	@GetMapping("/request")
	public List<LoanBook> getRequest() throws LibMsException {
		return loanBookService.getRequest();
	}

	/**
	 * api /loan to loan Book
	 * 
	 * @throws LibraryManagementException
	 * 
	 */

	@PostMapping("/loan")
	public LoanBook loanBook(@RequestBody String books) throws LibMsException {
		return loanBookService.loanBook(conveter.convertToId(books));
	}

	/**
	 * api /return to return book
	 * 
	 * @param book
	 * @throws LibraryManagementException
	 */

	@PostMapping("/return")
	public void returnBook(@RequestBody String data) throws LibMsException {
		 loanBookService.returnBook(conveter.convertToId(data));
	}
	@PostMapping("/disable")
	public void disable(@RequestBody String data) throws LibMsException {
		loanBookService.disable( conveter.convertToId(data)) ;
	}
	@GetMapping("books/{id}")
	public List<LoanBook> getRequestBook(@PathVariable("id") Long id) throws LibMsException {
		return loanBookService.getRequestByBook(id);

	}
	@GetMapping("user/{id}")
	public List<LoanBook> getRequestUser(@PathVariable("id") Long id) throws LibMsException {
		return loanBookService.getRequestByUser(id);

	}
	@PostMapping("/acceptLoan")
	public void acceptRequestBook(@RequestBody String data) throws LibMsException {
		 loanBookService.acceptLoaning(conveter.convertToId(data));

	}
	@PostMapping("/rejectLoan")
	public void rejectRequestUser(@RequestBody String data) throws LibMsException {
	   loanBookService.rejectLoaning(conveter.convertToId(data),conveter.convertToReason(data));

	}
	@PostMapping("/confiGetBook")
	public void confirmGetBook(@RequestBody String data) throws LibMsException {
	   loanBookService.confirmLoaning(conveter.convertToId(data));

	}
	@PostMapping("/confiReturnBook")
	public void confirmReturnBook(@RequestBody String data) throws LibMsException {
	   loanBookService.confirmReturn(conveter.convertToId(data));

	}
	@PostMapping("/loaningBook")
	public List<LoanBook> LoaningBook() throws LibMsException {
	    return loanBookService.loaningBook();

	}
	@GetMapping("/history")
	public Page<LoanBook> getHistory(Pageable pageable) throws LibMsException {
	    return loanBookService.history(pageable);

	}
	
	

}
