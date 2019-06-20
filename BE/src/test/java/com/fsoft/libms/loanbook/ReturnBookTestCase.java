package com.fsoft.libms.loanbook;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import com.fsoft.libms.BaseTestCase;
import com.fsoft.libms.model.Book;
import com.fsoft.libms.model.LoanBook;
import com.fsoft.libms.model.LoanStatus;
import com.fsoft.libms.models.Id;
import com.fsoft.libms.repository.BookRepository;
import com.fsoft.libms.repository.LoanBookRepository;
import com.fsoft.libms.repository.UserRepository;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ReturnBookTestCase extends BaseTestCase {
	@Autowired
	LoanBookRepository loanBookRepository;
	@Autowired
	BookRepository bookRepository;
	@Autowired
	private UserRepository userRepo;
	/**
	 * setup test case
	 */
	@Before
	public void setup() throws ParseException {

		Book book1 = new Book(1, "123", "sach 1", "tac gia 1", "nsx 1", (short) 1);
		Book book2 = new Book(2, "1234", "sach 2", "tac gia 2", "nsx 2", (short) 1);
		Book book3 = new Book(3, "1235", "sach 3", "tac gia 3", "nsx 3", (short) 2);
		Book book4 = new Book(4, "1236", "sach 4", "tac gia 4", "nsx 4", (short) 1);

		bookRepository.save(book1);
		bookRepository.save(book2);
		bookRepository.save(book3);
		bookRepository.save(book4);

		LoanBook loanBook1 = new LoanBook();
		loanBook1.setId(1);
		loanBook1.setBook(bookRepository.findById(2));
		loanBook1.setStatus(LoanStatus.WAITING);
		loanBook1.setUser(userRepo.findByUsername("usernormal"));
		loanBookRepository.save(loanBook1);
		LoanBook loanBook2 = new LoanBook();
		loanBook2.setId(2);
		loanBook2.setBook(bookRepository.findById(3));
		loanBook2.setStatus(LoanStatus.LOANING);
		loanBook2.setUser(userRepo.findByUsername("usernormal"));
		loanBookRepository.save(loanBook2);

	}

	/**
	 * test loan book successfully
	 */
	@Test
	public void testReturnBookSuccessfully() throws Exception {
		Id id = new Id(2);
		mockMvc.perform(post("/api/loan/return").header(HEADER_STRING, tokenUser()).content(asJsonString(id)))
				.andExpect(status().is(200)).andReturn();

	}

	/**
	 * test loan book fail with no auth
	 */
	@Test
	public void testReturnBookFailWithNoAuth() throws Exception {
		Id id = new Id(2);
		mockMvc.perform(post("/api/loan/return").header(HEADER_STRING, "test").content(asJsonString(id)))
				.andExpect(status().is(401)).andReturn();

	}

	/**
	 * test loan book fail with book not exist
	 */
	@Test
	public void testReturnBookFailWithBookNotExists() throws Exception {
		Id id = new Id(5);
		mockMvc.perform(post("/api/loan/return").header(HEADER_STRING, tokenUser()).content(asJsonString(id)))
				.andExpect(status().is(400)).andReturn();

	}

	/**
	 * test loan book fail with loaned book
	 */
	@Test
	public void testReturnBookFailWithBookAvailable() throws Exception {
		Id id = new Id(1);
		mockMvc.perform(post("/api/loan/return").header(HEADER_STRING, tokenUser()).content(asJsonString(id)))
				.andExpect(status().is(400)).andReturn();

	}

	


}
