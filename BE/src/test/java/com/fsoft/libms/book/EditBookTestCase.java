package com.fsoft.libms.book;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.codehaus.plexus.util.FileUtils;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.util.NestedServletException;

import com.fsoft.libms.BaseTestCase;
import com.fsoft.libms.exception.LibMsException;
import com.fsoft.libms.model.Book;
import com.fsoft.libms.profile.test.UploadImageServiceTest;
import com.fsoft.libms.repository.BookRepository;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EditBookTestCase extends BaseTestCase {
	@Autowired
	private BookRepository bookRepo;

	/**
	 * clean directory that contain file
	 * 
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	@After
	public void tearDown() throws IOException, URISyntaxException {
		FileUtils.cleanDirectory(new File(getPath(UploadImageServiceTest.folder).toUri()));
	}

	/**
	 * edit book successfully
	 * 
	 * @throws Exception
	 */

	@Test
	public void editBookSuccessfully() throws Exception {
		Book book = new Book();
		book.setAuthor("Má»… MÃ´ng");
		book.setDescription("book's description");
		book.setIsbn("123456789");
		book.setName("Sá»‘ng thá»±c táº¿ giá»¯a Ä‘á»�i");
		book.setPublisher("Kim Ä�á»“ng");
		book.setQuantity((short) 10);
		Book newBook = bookRepo.saveAndFlush(book);
		assertEquals(1, bookRepo.findAll().size());
		String data = "{\"name\":\"Cuoc song rung xanh\",\"quantity\":20}";

		mockMvc.perform(put("/api/book/" + newBook.getId()).contentType(MediaType.APPLICATION_JSON_VALUE).content(data)
				.header(HEADER_STRING, tokenAdmin())).andExpect(status().is(200));

		assertEquals(1, bookRepo.findAll().size());
		assertEquals("Cuoc song rung xanh", bookRepo.findById(newBook.getId()).getName());
		assertEquals(20, bookRepo.findById(newBook.getId()).getQuantity());

	}

	/**
	 * edit book failed by book does not exist
	 * 
	 * @throws Exception
	 */

	@Test
	public void editBookFailedAsBookNotExist() throws Exception {
		assertEquals(0, bookRepo.findAll().size());
		String data = "{\"name\":\"Cuoc song rung xanh\",\"quantity\":20}";
		MvcResult result = mockMvc.perform(put("/api/book/1").contentType(MediaType.APPLICATION_JSON_VALUE).content(data)
					.header(HEADER_STRING, tokenAdmin())).andExpect(status().is(400)).andReturn();
			assertEquals("Book not found!", result.getResponse().getContentAsString());
			assertEquals(0, bookRepo.findAll().size());

	}

	/**
	 * edit book failed as user does have permission
	 * 
	 * @throws Exception
	 */
	@Test
	public void editBookFailedAsUserNotHavePermission() throws Exception {
		assertEquals(0, bookRepo.findAll().size());
		String data = "{\"name\":\"Cuoc song rung xanh\",\"quantity\":20}";
		mockMvc.perform(put("/api/book/1").contentType(MediaType.APPLICATION_JSON_VALUE).content(data)
				.header(HEADER_STRING, tokenUser())).andExpect(status().is(500));

	}

}
