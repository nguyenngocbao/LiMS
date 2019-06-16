package com.fsoft.libms.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.fsoft.libms.exception.LibMsException;
import com.fsoft.libms.model.Book;

public interface IBookService {
	public Page<Book> getBooks(long categoryId, Pageable pageable);
	public void deleteBook(long id) throws LibMsException;
	public Book editBook(String data, long id) throws LibMsException;
	public void editBook(long id, String bookData, MultipartFile bookImageFile) throws LibMsException;
	public Book addBook(Book book, MultipartFile bookImageFile, long categoryId) throws LibMsException;
	public Book getBookById(long id);
	public Page<Book> getListBook(Pageable pageable);
}
