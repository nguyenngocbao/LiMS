package com.fsoft.libms.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fsoft.libms.exception.LibMsException;
import com.fsoft.libms.model.RequestNewBook;

public interface IRequestNewBookService {
	
	public RequestNewBook addRequest(String bookName, String author, String reason);
	
	public RequestNewBook accessRequest(long id, String message, boolean accept) throws LibMsException;
	
	public Page<RequestNewBook> getRequest(Pageable pageable);
	public Page<RequestNewBook> getRequestByUser(Pageable pageable);

}
