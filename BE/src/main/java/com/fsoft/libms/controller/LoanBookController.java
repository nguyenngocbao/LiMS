package com.fsoft.libms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsoft.libms.exception.LibMsException;
import com.fsoft.libms.service.ILoanBookService;
import com.fsoft.libms.uitl.LimsConverter;

@RestController
@RequestMapping("/loan")
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
	public long reserveBook(@RequestBody String books) throws LibMsException {
		return conveter.convertToId(books);

	}

	/**
	 * api /loan to loan Book
	 * 
	 * @throws LibraryManagementException
	 * 
	 */

	@PostMapping("/loan")
	public long loanBook(@RequestBody String books) throws LibMsException {
		return conveter.convertToId(books);
	}

	/**
	 * api /return to return book
	 * 
	 * @param book
	 * @throws LibraryManagementException
	 */

	@PostMapping("/return")
	public long returnBook(@RequestBody String data) throws LibMsException {
		return conveter.convertToId(data);
	}

}
