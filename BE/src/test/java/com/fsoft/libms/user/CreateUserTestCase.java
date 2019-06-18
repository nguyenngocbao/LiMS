package com.fsoft.libms.user;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.apache.logging.log4j.core.appender.FailoversPlugin;
import org.codehaus.plexus.util.FileUtils;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;

import com.fsoft.libms.BaseTestCase;
import com.fsoft.libms.exception.LibMsException;
import com.fsoft.libms.model.Role;
import com.fsoft.libms.model.User;
import com.fsoft.libms.profile.test.UploadImageServiceTest;
import com.fsoft.libms.repository.RoleRepository;
import com.fsoft.libms.repository.UserRepository;

public class CreateUserTestCase extends BaseTestCase{
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
	 * @throws URISyntaxException 
	 * @throws IOException 
     */
    @After
    public void tearDown() throws IOException, URISyntaxException {
	userRepo.deleteAll();
	FileUtils.cleanDirectory( new File( getPath( UploadImageServiceTest.folderAvatar ).toUri() ) );
    }
    
    /**
     * create user successfully
     * 
     * @throws Exception
     */
    @Test
    public void testCreateUserSuccessfully() throws Exception {
	    String data = "{\"username\":\"tttthuy\",\"password\":\"tttthuy123\",\"retypePassword\":\"tttthuy123\",\"email\":\"tttthuy@gmail.com\",\"fullName\":\"Tran Thi Thu Thuy\"}";
	    MockMultipartFile avatar = new MockMultipartFile( "file", "user.png", "text/plain", loadFile("user.png"));
	    mockMvc.perform( fileUpload( "/api/user/create").file(avatar).param("data", data)).andExpect(status().is( 200 ));
		assertEquals( 3, userRepo.findAll().size() );
		User userCreate = userRepo.findByUsername( "tttthuy" );
		assertFalse( userCreate.equals( null ) );
		assertEquals( "tttthuy", userCreate.getUsername() );
		Set<Role> roles = roleRepo.findByAuthority("USER");
		assertEquals( roles.size(), userCreate.getAuthorities().size() );
		assertTrue( userCreate.getAuthorities().containsAll(roles));
	
    }
    
    /**
     * create user fail as this user exists
     * 
     * @throws Exception
     */
    @Test
    public void testCreateUserFailAsUserExists() throws Exception {
    	 String data = "{\"username\":\"usernormal\",\"password\":\"tttthuy123\",\"retypePassword\":\"tttthuy123\",\"email\":\"tttthuy@gmail.com\",\"fullName\":\"Tran Thi Thu Thuy\"}";
    	  MockMultipartFile avatar = new MockMultipartFile( "file", "user.png", "text/plain", loadFile("user.png"));
    	 try {
    	  mockMvc.perform( fileUpload( "/api/user/create").file(avatar).param("data", data)).andExpect(status().is( 400 )).andReturn();
    	  fail("Exception not thrown");
    	 } catch(Exception e) {
    		 assertEquals( USER_EXIST, e.getCause().getMessage());
    	 }
    }
    

}
