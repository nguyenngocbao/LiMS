package com.fsoft.libms.user;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

import org.codehaus.plexus.util.FileUtils;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.util.NestedServletException;

import com.fsoft.libms.BaseTestCase;
import com.fsoft.libms.exception.LibMsException;
import com.fsoft.libms.model.Role;
import com.fsoft.libms.model.User;
import com.fsoft.libms.profile.test.UploadImageServiceTest;
import com.fsoft.libms.repository.RoleRepository;
import com.fsoft.libms.repository.UserRepository;

public class CreateUserTestCase extends BaseTestCase {
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	private static final String USER_EXIST = "Username already exists";
	private static final String USER_NOT_FOUND = "Username '%s' not found";
	private static final String WRONG_PASSWORD = "Wrong password";
	private static final String LENG_USERNAME_INVALID = " Username must be at least 3 characters long and smaller 20 characters long";
	private static final String INVALID_EMAIL = "Invalid email";
	private static final String SHORT_PASSWORD = "Password is too short";

	/**
	 * reset db
	 * 
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	@After
	public void tearDown() throws IOException, URISyntaxException {
		userRepo.deleteAll();
		FileUtils.cleanDirectory(new File(getPath(UploadImageServiceTest.folderAvatar).toUri()));
	}

	/**
	 * create user successfully
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateUserSuccessfully() throws Exception {
		String data = "{\"username\":\"tttthuy\",\"password\":\"tttthuy123\",\"retypePassword\":\"tttthuy123\",\"email\":\"tttthuy@gmail.com\",\"fullName\":\"Tran Thi Thu Thuy\"}";
		MockMultipartFile avatar = new MockMultipartFile("file", "user.png", "text/plain", loadFile("user.png"));
		mockMvc.perform(fileUpload("/api/user/create").file(avatar).param("data", data)).andExpect(status().is(200));
		assertEquals(3, userRepo.findAll().size());
		User userCreate = userRepo.findByUsername("tttthuy");
		assertFalse(userCreate.equals(null));
		assertEquals("tttthuy", userCreate.getUsername());
		Set<Role> roles = roleRepo.findByAuthority("USER");
		assertEquals(roles.size(), userCreate.getAuthorities().size());
		assertTrue(userCreate.getAuthorities().containsAll(roles));

	}

	/**
	 * create user fail as this user exists
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateUserFailAsUserExists() throws Exception {
		String data = "{\"username\":\"usernormal\",\"password\":\"tttthuy123\",\"retypePassword\":\"tttthuy123\",\"email\":\"tttthuy@gmail.com\",\"fullName\":\"Tran Thi Thu Thuy\"}";
		MockMultipartFile avatar = new MockMultipartFile("file", "user.png", "text/plain", loadFile("user.png"));
		try {
			mockMvc.perform(fileUpload("/api/user/create").file(avatar).param("data", data)).andExpect(status().is(400))
					.andReturn();
			fail("Exception not thrown");
		} catch (Exception e) {
			assertEquals(USER_EXIST, e.getCause().getMessage());
			assertEquals(2, userRepo.findAll().size());
		}
	}

	/**
	 * create user fail as username field does not input
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateUserFailAsUsernameNotInput() throws Exception {
		String data = "{\"password\":\"tttthuy123\",\"retypePassword\":\"tttthuy123\",\"email\":\"tttthuy@gmail.com\",\"fullName\":\"Tran Thi Thu Thuy\"}";
		MockMultipartFile avatar = new MockMultipartFile("file", "user.png", "text/plain", loadFile("user.png"));
		try {
			mockMvc.perform(fileUpload("/api/user/create").file(avatar).param("data", data)).andExpect(status().is(400))
					.andReturn();
			fail("Exception not thrown");
		} catch (Exception e) {
			assertEquals("Username is required", e.getCause().getMessage());
			assertEquals(2, userRepo.findAll().size());
		}
	}

	/**
	 * create user fail as this user exists
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateUserFailAsPasswordNotInput() throws Exception {
		String data = "{\"username\":\"usernormal\",\"retypePassword\":\"tttthuy123\",\"email\":\"tttthuy@gmail.com\",\"fullName\":\"Tran Thi Thu Thuy\"}";
		MockMultipartFile avatar = new MockMultipartFile("file", "user.png", "text/plain", loadFile("user.png"));
		try {
			mockMvc.perform(fileUpload("/api/user/create").file(avatar).param("data", data)).andExpect(status().is(400))
					.andReturn();
			fail("Exception not thrown");
		} catch (Exception e) {
			assertEquals("Password is required", e.getCause().getMessage());
			assertEquals(2, userRepo.findAll().size());
		}
	}

	/**
	 * create user failed as fullname field does not input
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateUserFailAsFullNameNotInput() throws Exception {
		String data = "{\"username\":\"tttthuy\",\"password\":\"tttthuy123\",\"retypePassword\":\"tttthuy123\",\"email\":\"tttthuy@gmail.com\"}";
		MockMultipartFile avatar = new MockMultipartFile("file", "user.png", "text/plain", loadFile("user.png"));
		try {
			mockMvc.perform(fileUpload("/api/user/create").file(avatar).param("data", data)).andExpect(status().is(400))
					.andReturn();
			fail("Exception not thrown");
		} catch (Exception e) {
			assertEquals("Full name is required", e.getCause().getMessage());
			assertEquals(2, userRepo.findAll().size());
		}

	}

	/**
	 * create user failed as email field does not input
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateUserFailAsEmailNotInput() throws Exception {
		String data = "{\"username\":\"tttthuy\",\"password\":\"tttthuy123\",\"retypePassword\":\"tttthuy123\",\"fullName\":\"Tran Thi Thu Thuy\"}";
		MockMultipartFile avatar = new MockMultipartFile("file", "user.png", "text/plain", loadFile("user.png"));
		try {
			mockMvc.perform(fileUpload("/api/user/create").file(avatar).param("data", data)).andExpect(status().is(400))
					.andReturn();
			fail("Exception not thrown");
		}  catch (NestedServletException e) {
			assertTrue(e.getCause() instanceof LibMsException);
			assertEquals("Email is required", e.getCause().getMessage());
			assertEquals(2, userRepo.findAll().size());
		}

	}

	/**
	 * create user failed as email field does not input
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateUserFailAsPasswordsNotMatched() throws Exception {
		String data = "{\"username\":\"tttthuy\",\"password\":\"tttthuy1234\",\"retypePassword\":\"tttthuy123\",\"email\":\"tttthuy@gmail.com\",\"fullName\":\"Tran Thi Thu Thuy\"}";
		MockMultipartFile avatar = new MockMultipartFile("file", "user.png", "text/plain", loadFile("user.png"));
		try {
			mockMvc.perform(fileUpload("/api/user/create").file(avatar).param("data", data)).andExpect(status().is(400))
					.andReturn();
			fail("Exception not thrown");
		} catch (Exception e) {
			assertEquals("Password and retype password are not matched", e.getCause().getMessage());
			assertEquals(2, userRepo.findAll().size());
		}

	}
	
	/**
	 * create user failed as email field is invalid
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateUserFailAsEmailInvalid() throws Exception {
		String data = "{\"username\":\"tttthuy\",\"password\":\"tttthuy123\",\"retypePassword\":\"tttthuy123\",\"email\":\"tttthuy\",\"fullName\":\"Tran Thi Thu Thuy\"}";
		MockMultipartFile avatar = new MockMultipartFile("file", "user.png", "text/plain", loadFile("user.png"));
		try {
			mockMvc.perform(fileUpload("/api/user/create").file(avatar).param("data", data)).andExpect(status().is(400))
					.andReturn();
			fail("Exception not thrown");
		} catch (Exception e) {
			assertEquals(INVALID_EMAIL, e.getCause().getMessage());
			assertEquals(2, userRepo.findAll().size());
		}

	}
	
	/**
	 * 
	 * create user successfully by admin user
	 * */
	
	@Test
	public void testCreateUserSuccessfullyByAdmin() throws Exception {
		String data = "{\"role\": \"TEACHER\", \"username\":\"tttthuy\",\"password\":\"tttthuy123\",\"retypePassword\":\"tttthuy123\",\"email\":\"tttthuy@gmail.com\",\"fullName\":\"Tran Thi Thu Thuy\"}";
		MockMultipartFile avatar = new MockMultipartFile("file", "user.png", "text/plain", loadFile("user.png"));
		mockMvc.perform(fileUpload("/api/user/admin/create").file(avatar).param("data", data)
				.header( HEADER_STRING, tokenAdmin() )).andExpect(status().is(200));
		assertEquals(3, userRepo.findAll().size());
		User userCreate = userRepo.findByUsername("tttthuy");
		assertFalse(userCreate.equals(null));
		assertEquals("tttthuy", userCreate.getUsername());
		Set<Role> roles = roleRepo.findByAuthority("TEACHER");
		assertEquals(roles.size(), userCreate.getAuthorities().size());
		assertTrue(userCreate.getAuthorities().containsAll(roles));
	}
	
	/**
	 * 
	 * create user failed by user created that is not admin
	 * */
	
//	@Test
//	public void testCreateUserFailedByNotAdmin() throws Exception {
//		String data = "{\"role\": \"TEACHER\", \"username\":\"tttthuy\",\"password\":\"tttthuy123\",\"retypePassword\":\"tttthuy123\",\"email\":\"tttthuy@gmail.com\",\"fullName\":\"Tran Thi Thu Thuy\"}";
//		MockMultipartFile avatar = new MockMultipartFile("file", "user.png", "text/plain", loadFile("user.png"));
//		mockMvc.perform(fileUpload("/api/user/admin/create").file(avatar).param("data", data)
//				.header( HEADER_STRING, tokenUser() )).andExpect(status().is(403));
//		assertEquals(2, userRepo.findAll().size());
//		User userCreate = userRepo.findByUsername("tttthuy");
//		assertTrue(userCreate == null);
//	}
//	
	

}
