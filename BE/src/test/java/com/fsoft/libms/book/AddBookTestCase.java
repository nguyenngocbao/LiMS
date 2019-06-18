package com.fsoft.libms.book;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.codehaus.plexus.util.FileUtils;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.util.NestedServletException;

import com.fsoft.libms.BaseTestCase;
import com.fsoft.libms.exception.LibMsException;
import com.fsoft.libms.model.Book;
import com.fsoft.libms.profile.test.UploadImageServiceTest;
import com.fsoft.libms.repository.BookRepository;
@DirtiesContext( classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD )
public class AddBookTestCase extends BaseTestCase{
	
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
	 * add book successfully
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddBookSuccessfully() throws Exception {
		Book book = new Book();
		book.setAuthor("Mễ Mông");
		book.setDescription("book's description");
		book.setIsbn("123456789");
		book.setName("Sống thực tế giữa đời");
		book.setPublisher("Kim Đồng");
		book.setQuantity((short) 10);
		MockMultipartFile image = new MockMultipartFile("file", "book.png", "text/plain", loadFile("book.png"));
		mockMvc.perform(fileUpload("/api/book/category/1").file(image).param("data", asJsonString(book))
				.header( HEADER_STRING, tokenAdmin() ))
		.andExpect(status().is(200));
		assertEquals(1, bookRepo.findAll().size());
		Book bookCreated = bookRepo.findByIsbn("123456789");
		assertFalse(bookCreated.equals(null));
		assertEquals("Sống thực tế giữa đời", bookCreated.getName());

	}
	
	/**
	 * add book failed as permission denied
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddBookFailedAsPermissionDenied() throws Exception {
		Book book = new Book();
		book.setAuthor("Mễ Mông");
		book.setDescription("book's description");
		book.setIsbn("123456789");
		book.setName("Sống thực tế giữa đời");
		book.setPublisher("Kim Đồng");
		book.setQuantity((short) 10);
		MockMultipartFile image = new MockMultipartFile("file", "book.png", "text/plain", loadFile("book.png"));
		mockMvc.perform(fileUpload("/api/book/category/1").file(image).param("data", asJsonString(book))
				.header( HEADER_STRING, tokenUser() ))
		.andExpect(status().is(403));
		assertEquals(0, bookRepo.findAll().size());
		Book bookCreated = bookRepo.findByIsbn("123456789");
		assertTrue(bookCreated == null);

	}
	
	/**
	 * add book failed as permission denied
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddBookFailedAsCategoryNotExits() throws Exception {
		Book book = new Book();
		book.setAuthor("Mễ Mông");
		book.setDescription("book's description");
		book.setIsbn("123456789");
		book.setName("Sống thực tế giữa đời");
		book.setPublisher("Kim Đồng");
		book.setQuantity((short) 10);
		MockMultipartFile image = new MockMultipartFile("file", "book.png", "text/plain", loadFile("book.png"));
		try {
		mockMvc.perform(fileUpload("/api/book/category/2").file(image).param("data", asJsonString(book))
				.header( HEADER_STRING, tokenAdmin() ))
		.andExpect(status().is(400));
		fail("Throws exception");
		} catch (NestedServletException e) {
			assertTrue(e.getCause() instanceof LibMsException);
			assertEquals("Category does not exist!", e.getCause().getMessage());
			assertEquals(0, bookRepo.findAll().size());
			Book bookCreated = bookRepo.findByIsbn("123456789");
			assertTrue(bookCreated == null);
		}
	}
		
		/**
		 * add book failed as book already exist
		 * 
		 * @throws Exception
		 */
		@Test
		public void testAddBookFailedAsBookAldreadyExist() throws Exception {
			Book book = new Book();
			book.setAuthor("Mễ Mông");
			book.setDescription("book's description");
			book.setIsbn("123456789");
			book.setName("Sống thực tế giữa đời");
			book.setPublisher("Kim Đồng");
			book.setQuantity((short) 10);
			bookRepo.save(book);
			assertEquals(1, bookRepo.findAll().size());
			MockMultipartFile image = new MockMultipartFile("file", "book.png", "text/plain", loadFile("book.png"));
			try {
			mockMvc.perform(fileUpload("/api/book/category/1").file(image).param("data", asJsonString(book))
					.header( HEADER_STRING, tokenAdmin() ))
			.andExpect(status().is(400));
			fail("Throws exception");
			} catch (NestedServletException e) {
				assertTrue(e.getCause() instanceof LibMsException);
				assertEquals("Book already exists!", e.getCause().getMessage());
				assertEquals(1, bookRepo.findAll().size());
			}

	}
	
	

}
