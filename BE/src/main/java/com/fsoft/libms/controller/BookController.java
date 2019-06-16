package com.fsoft.libms.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsoft.libms.exception.LibMsException;
import com.fsoft.libms.model.Book;
import com.fsoft.libms.service.impl.BookService;

@RestController
@RequestMapping("/api/book")
public class BookController {
	@Autowired
	private BookService bookService;

	@PutMapping(value = "/{id}")
	public Book editBook(@RequestBody String data, @PathVariable("id") Long id) throws LibMsException {
		return bookService.editBook(data, id);
	}

	@PostMapping(value = "/file/{id}")
	public void editBookAndUploadImage(@RequestParam String data, @PathVariable("id") Long id,
			@RequestParam("file") MultipartFile file) throws LibMsException {
		 bookService.editBook(id, data, file);
	}
	@GetMapping(value = "/test")
	public Book test() throws LibMsException {
		return bookService.test();
	}

	@PostMapping(value="/category/{id}")
	public Book addBook(@RequestParam String data, @RequestParam("file") MultipartFile file, @PathVariable("id") Long categoryId)
			throws LibMsException, JsonParseException, JsonMappingException, IOException {
		Book book = new ObjectMapper().readValue(data, Book.class);
		return bookService.addBook(book, file, categoryId);
	}

	@DeleteMapping(value= "/{id}")
	public void deleteBook(@PathVariable long id) throws LibMsException {
		bookService.deleteBook(id);
	}
	
	@GetMapping(value = "/{id}")
	public Book getBook(@PathVariable long id) throws LibMsException {
		return bookService.getBookById(id);
	}

	@GetMapping(value="/category/{id}")
	public Page<Book> getBooks(@PathVariable("id") Long categoryId, Pageable pageable) {
		return bookService.getBooks(categoryId, pageable);
	}
	
	@GetMapping()
	public Page<Book> getAllBook(Pageable pageable) {
		return bookService.getListBook(pageable);
	}
	
}
