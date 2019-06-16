package com.fsoft.libms.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fsoft.libms.exception.LibMsException;
import com.fsoft.libms.model.RequestNewBook;
import com.fsoft.libms.model.Roles;
import com.fsoft.libms.service.IRequestNewBookService;


@RestController
@RequestMapping("/api/request")
public class RequestNewBookController {
	@Autowired
	private IRequestNewBookService requestService;
	
	
	@PostMapping()
	public RequestNewBook addRequestNewBook(@RequestParam("author") String author, @RequestParam("reason") String reason, @RequestParam("bookname") String bookName)
	{
		return requestService.addRequest(bookName, author, reason);
	}
	@Secured(Roles.ADMIN)
	@PostMapping(value = "/{id}")
	public RequestNewBook accessRequestNewBook(@PathVariable long id, @RequestParam("message") String message, @RequestParam("accept") boolean accept) throws LibMsException
	{
		return requestService.accessRequest(id,message, accept);
	}
	
	@GetMapping()
	public Page<RequestNewBook> getRequestNewBook (Pageable pageable) {
		return requestService.getRequest(pageable);
	}
	
	@GetMapping(value="/user")
	public Page<RequestNewBook> getRequestNewBookByUser (Pageable pageable) {
		return requestService.getRequestByUser(pageable);
	}
	
	

}
