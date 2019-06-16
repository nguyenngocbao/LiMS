package com.fsoft.libms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fsoft.libms.exception.LibMsException;
import com.fsoft.libms.model.RequestNewBook;
import com.fsoft.libms.model.User;
import com.fsoft.libms.repository.RequestNewBookRepository;
import com.fsoft.libms.repository.UserRepository;
import com.fsoft.libms.security.token.TokenProvider;
import com.fsoft.libms.service.IRequestNewBookService;
@Service
public class RequestNewBookService implements IRequestNewBookService {

	@Autowired
	private TokenProvider tokenProvider;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RequestNewBookRepository requestRepo;
	
	public RequestNewBook addRequest(String bookName, String author, String reason) {
		
		String username = tokenProvider.getAuthToken().getName();
		User user = userRepo.findByUsername(username);
		RequestNewBook request = new RequestNewBook();
		request.setBookName(bookName);
		request.setAuthor(author);
		request.setReason(reason);
		request.setUser(user);
		return requestRepo.saveAndFlush(request);
		
	}

	@Override
	public RequestNewBook accessRequest(long id, String message, boolean accept) throws LibMsException {
		RequestNewBook request  = requestRepo.findOne(id);
		if (request  == null) {
			throw new LibMsException("Request này không tồn tại");
		}
		request.setResponse(true);
		request.setMessage(message);
		request.setAccepted(accept);
		return requestRepo.saveAndFlush(request);
		
		
		
		
		
	}
	public Page<RequestNewBook> getRequest(Pageable pageable){
	return requestRepo.findAll(pageable);
		
			
	}
	public Page<RequestNewBook> getRequestByUser(Pageable pageable){
		String username = tokenProvider.getAuthToken().getName();
		User user = userRepo.findByUsername(username);
		return requestRepo.findByUser(user, pageable);
			
				
		}

}
