package com.fsoft.libms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsoft.libms.exception.LibMsException;
import com.fsoft.libms.model.Book;
import com.fsoft.libms.model.Category;
import com.fsoft.libms.repository.BookRepository;
import com.fsoft.libms.repository.CategoryRepository;
import com.fsoft.libms.service.AbstractService;
import com.fsoft.libms.service.IBookService;
import com.fsoft.libms.service.IUploadImageService;

@Service
@Transactional(readOnly = true)
public class BookService extends AbstractService implements IBookService {
	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private ObjectMapper objMapper;

	@Autowired
	private IUploadImageService uploadFile;

	public static final String BOOK_EXIST = "Book already exists!";
	public static final String CATEGORY_NOT_EXIST = "Category does not exist!";
	public static final String BOOK_NOT_FOUND = "Book not found!";
	public static final String BOOK_NAME = "/name";
	public static final String BOOK_DESCRIPTION = "/description";
	public static final String BOOK_CATEGORY = "/category";
	public static final String BOOK_ISBN = "/isbn";
	public static final String BOOK_AUTHOR = "/author";
	public static final String BOOK_PUBLISHER = "/publisher";
	public static final String BOOK_QUANTITY = "/quantity";

	/**
	 * add book into library if this book doesn't exist
	 * 
	 * @param book
	 */
	@Transactional
	public Book addBook(Book book, MultipartFile bookImageFile, long categoryId) throws LibMsException {
		if (bookRepository.findByIsbn(book.getIsbn()) == null) {
			Category category = categoryRepo.findById(categoryId);
			if (category != null) {
				book.setCategory(category);
				String fileName = String.format("%s-%s", book.getIsbn(), bookImageFile.getOriginalFilename());
				book.setImage(String.format("%s%s", "/api/upload/", fileName));
				book.setDateCreated(new Date());
				book = bookRepository.saveAndFlush(book);
				uploadFile.storeFile(bookImageFile, fileName);
				return book;
			} else {
				throw new LibMsException(CATEGORY_NOT_EXIST);
			}
		} else {
			throw new LibMsException(BOOK_EXIST);
		}
	}

	/**
	 * edit book information if this book exists
	 */
	@Transactional
	public void editBook(long id, String bookData, MultipartFile bookImageFile) throws LibMsException {
		Book bookEdit = bookRepository.findById(id);
		if (bookEdit != null) {
			JsonNode node;
			try {
				node = objMapper.readTree(bookData);
				JsonNode name = node.at(BOOK_NAME);
				JsonNode description = node.at(BOOK_DESCRIPTION);
				JsonNode isbn = node.at(BOOK_ISBN);
				JsonNode author = node.at(BOOK_AUTHOR);
				JsonNode publisher = node.at(BOOK_PUBLISHER);
				JsonNode quantity = node.at(BOOK_QUANTITY);
				JsonNode categoryId = node.at(BOOK_CATEGORY);
				String fileName = String.format("%s-%s", bookEdit.getIsbn(), bookImageFile.getOriginalFilename());
				uploadFile.deleteFile(bookEdit.getImage().replace("/api/upload/", ""));
				bookEdit.setImage(String.format("%s%s", "/api/upload/", fileName));
				if (!categoryId.isMissingNode()) {
					Category category = categoryRepo.findById(categoryId.asLong());
					if (category != null) {
						bookEdit.setCategory(category);
					} else {
						throw new LibMsException("Category with id " + categoryId.asLong() + " not found");
					}
				}

				if (!name.isMissingNode()) {
					bookEdit.setName(name.asText());
				}
				if (!description.isMissingNode()) {
					bookEdit.setDescription(description.asText());
				}
				if (!isbn.isMissingNode()) {
					bookEdit.setIsbn(isbn.asText());
				}
				if (!author.isMissingNode()) {
					bookEdit.setAuthor(author.asText());
				}
				if (!publisher.isMissingNode()) {
					bookEdit.setPublisher(publisher.asText());
				}
				if (!quantity.isMissingNode()) {
					bookEdit.setQuantity((short)quantity.asInt());
				}
			
				bookRepository.save(bookEdit);
				uploadFile.storeFile(bookImageFile, fileName);
			} catch (Exception e) {
				LOGGER.error("Unable to read input. " + e.getMessage(), e);
				throw new LibMsException("Unable to read input. " + e.getMessage());
			}

		} else {
			throw new LibMsException(BOOK_NOT_FOUND);
		}
	
	}
@Transactional
	public Book editBook(String data, long id) throws LibMsException {
		Book bookEdit = bookRepository.findById(id);
		if (bookEdit != null) {
			JsonNode node;
			try {
				node = objMapper.readTree(data);
				JsonNode name = node.at(BOOK_NAME);
				JsonNode description = node.at(BOOK_DESCRIPTION);
				JsonNode isbn = node.at(BOOK_ISBN);
				JsonNode author = node.at(BOOK_AUTHOR);
				JsonNode publisher = node.at(BOOK_PUBLISHER);
				JsonNode quantity = node.at(BOOK_QUANTITY);
				JsonNode categoryId = node.at(BOOK_CATEGORY);

				if (!categoryId.isMissingNode()) {
					Category category = categoryRepo.findById(categoryId.asLong());
					if (category != null) {
						bookEdit.setCategory(category);
					} else {
						throw new LibMsException("Category with id " + categoryId.asLong() + " not found");
					}
				}
				if (!name.isMissingNode()) {
					bookEdit.setName(name.asText());
				}
				if (!description.isMissingNode()) {
					bookEdit.setDescription(description.asText());
				}
				if (!isbn.isMissingNode()) {
					bookEdit.setIsbn(isbn.asText());
				}
				if (!author.isMissingNode()) {
					bookEdit.setAuthor(author.asText());
				}
				if (!publisher.isMissingNode()) {
					bookEdit.setPublisher(publisher.asText());
				}
				if (!quantity.isMissingNode()) {
					bookEdit.setQuantity((short)quantity.asInt());
				}
				return bookRepository.saveAndFlush(bookEdit);
			} catch (Exception e) {
				LOGGER.error("Unable to read input. " + e.getMessage(), e);
				throw new LibMsException("Unable to read input. " + e.getMessage());
			}

		} else {
			throw new LibMsException(BOOK_NOT_FOUND);
		}
	}

	/**
	 * delete book
	 */
	@Transactional
	public void deleteBook(long id) throws LibMsException {
		Book book = bookRepository.findById(id);
		if (book != null) {
			bookRepository.delete(id);
			if (book.getImage() != null) {
				uploadFile.deleteFile(book.getImage().replace("/api/upload/", ""));
			}
		} else {
			throw new LibMsException(BOOK_NOT_FOUND);
		}

	}

	public Page<Book> getBooks(long categoryId, Pageable pageable) {
		Category category = categoryRepo.findById(categoryId);
		if (category != null ) {
			return bookRepository.findByCategory(category, pageable);
		} else {
			List<Book> books = new ArrayList<Book>();
			Page<Book> page = new PageImpl<>(books, pageable, books.size());
			return page;
		}
	}
	
	public Page<Book> getListBook(Pageable pageable) {
		return bookRepository.findAll(pageable);
	}

	public Book getBookById(long id) {
		return bookRepository.findById(id);
	}
}
