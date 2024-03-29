package com.fsoft.libms.loanbook;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;

import com.fsoft.libms.BaseTestCase;
import com.fsoft.libms.model.Book;
import com.fsoft.libms.model.LoanBook;
import com.fsoft.libms.model.LoanStatus;
import com.fsoft.libms.models.Id;
import com.fsoft.libms.repository.BookRepository;
import com.fsoft.libms.repository.LoanBookRepository;
import com.fsoft.libms.repository.UserRepository;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ReserveBookTestCase extends BaseTestCase {
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
		Book book2 = new Book(2, "1234", "sach 2", "tac gia 2", "nsx 2", (short) 2);
		Book book3 = new Book(3, "1235", "sach 3", "tac gia 3", "nsx 3", (short) 2);
		Book book4 = new Book(4, "1236", "sach 4", "tac gia 4", "nsx 4", (short) 1);
		Book book5 = new Book(5, "12536", "sach 5", "tac gia 5", "nsx 5", (short) 2);

		bookRepository.save(book1);
		bookRepository.save(book2);
		bookRepository.save(book3);
		bookRepository.save(book4);
		bookRepository.save(book5);

		LoanBook loanBook1 = new LoanBook();
		loanBook1.setBook(bookRepository.findById(1));
		loanBook1.setStatus(LoanStatus.WAITING);
		loanBook1.setUser(userRepo.findByUsername("usernormal"));
		loanBookRepository.save(loanBook1);
		LoanBook loanBook2 = new LoanBook();
		loanBook2.setBook(bookRepository.findById(3));
		loanBook2.setStatus(LoanStatus.LOANING);
		loanBook2.setUser(userRepo.findByUsername("usernormal"));
		loanBookRepository.save(loanBook2);
		LoanBook loanBook3 = new LoanBook();
		loanBook3.setBook(bookRepository.findById(2));
		loanBook3.setStatus(LoanStatus.LOANING);
		loanBook3.setUser(userRepo.findByUsername("admin"));
		loanBookRepository.save(loanBook3);
		LoanBook loanBook4 = new LoanBook();
		loanBook4.setBook(bookRepository.findById(4));
		loanBook4.setStatus(LoanStatus.RESERVE);
		loanBook4.setUser(userRepo.findByUsername("admin"));
		loanBookRepository.save(loanBook4);
		LoanBook loanBook5 = new LoanBook();
		loanBook5.setBook(bookRepository.findById(2));
		loanBook5.setStatus(LoanStatus.WAITING);
		loanBook5.setUser(userRepo.findByUsername("admin"));
		loanBookRepository.save(loanBook5);

	}

	/**
	 * test loan book successfully
	 */
	@Test
	public void testRerseveBookSuccessfully() throws Exception {
		Id id = new Id(2);
		MvcResult result = mockMvc.perform(post("/api/loan/reserve").header(HEADER_STRING, tokenUser()).content(asJsonString(id)))
				.andExpect(status().is(200)).andReturn();
		
		

	}

	/**
	 * test loan book fail with no auth
	 */
	@Test
	public void testRerseveBookFailWithNoAuth() throws Exception {
		Id id = new Id(2);
		mockMvc.perform(post("/api/loan/reserve").header(HEADER_STRING, "test").content(asJsonString(id)))
				.andExpect(status().is(401)).andReturn();

	}

	/**
	 * test loan book fail with book not exist
	 */
	@Test
	public void testRerseveBookFailWithBookNotExists() throws Exception {
		Id id = new Id(6);
		MvcResult result = mockMvc.perform(post("/api/loan/reserve").header(HEADER_STRING, tokenUser()).content(asJsonString(id)))
				.andExpect(status().is(400)).andReturn();
		assertEquals("khong ton tai", result.getResponse().getContentAsString());

	}

	/**
	 * test loan book fail with loaned book
	 */
	@Test
	public void testRerseveBookFailWithBookAvailable() throws Exception {
		Id id = new Id(5);
		MvcResult result = mockMvc.perform(post("/api/loan/reserve").header(HEADER_STRING, tokenUser()).content(asJsonString(id)))
				.andExpect(status().is(400)).andReturn();
		assertEquals("Sách có thể mượn được", result.getResponse().getContentAsString());

	}

	/**
	 * test loan book fail with loaned book
	 */
	@Test
	public void testRerseveBookFailWithLoanedBook() throws Exception {
		
		Id id = new Id(1);
		MvcResult result = mockMvc.perform(post("/api/loan/reserve").header(HEADER_STRING, tokenUser()).content(asJsonString(id)))
				.andExpect(status().is(400)).andReturn();
		assertEquals("Bạn đã đang yêu cầu hoặc mượn quyển sách này rồi", result.getResponse().getContentAsString());

	}
	/**
	 * test loan book fail with loaned book
	 */
	@Test
	public void testRerseveBookFailWithLoanedBookOver3() throws Exception {
		LoanBook loanBook2 = new LoanBook();
		loanBook2.setBook(bookRepository.findById(5));
		loanBook2.setStatus(LoanStatus.LOANING);
		loanBook2.setUser(userRepo.findByUsername("usernormal"));
		loanBookRepository.save(loanBook2);
		Id id = new Id(2);
		MvcResult result = mockMvc.perform(post("/api/loan/reserve").header(HEADER_STRING, tokenUser()).content(asJsonString(id)))
				.andExpect(status().is(400)).andReturn();
		assertEquals("Bạn đã mượn quá 3 quyển sách", result.getResponse().getContentAsString());

	}

	/**
	 * test loan book fail with loaned book
	 */
	@Test
	public void testRerseveBookFailWithLoanedBookOverReserve() throws Exception {
		
		Id id = new Id(4);
		MvcResult result = mockMvc.perform(post("/api/loan/reserve").header(HEADER_STRING, tokenUser()).content(asJsonString(id)))
				.andExpect(status().is(400)).andReturn();
		assertEquals("Sách vượt quá số lượng cho đặt trước", result.getResponse().getContentAsString());

	}

}
