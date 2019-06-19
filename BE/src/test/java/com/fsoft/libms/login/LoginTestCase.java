package com.fsoft.libms.login;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

import com.fsoft.libms.security.token.TokenProvider;
import com.fsoft.libms.security.token.UserTokenState;
import com.fsoft.libms.BaseTestCase;
import com.fsoft.libms.model.Role;
import com.fsoft.libms.model.User;
import com.fsoft.libms.repository.RoleRepository;
import com.fsoft.libms.repository.UserRepository;

public class LoginTestCase extends BaseTestCase {
	@Autowired
	private TokenProvider tokenProvider;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	/**
	 * Test login successfully
	 * 
	 *
	 * */
    @Test
    public void testLoginSuccessfully() throws Exception {
	mockMvc.perform( post( "/login" ).param( "username", "admin" ).param( "password", "admin" ) ).andExpect( status().is( 200 ) );

    }

    /**
     * Test login Failed, password is wrong
     * 
     * @throws Exception
     */
    @Test
    public void testLoginFailed() throws Exception {
	mockMvc.perform( post( "/login" ).param( "username", "admin" ).param( "password", "123" ) ).andExpect( status().is( 401 ) );
    }
    
    /**
     * Test login Failed, username and password are wrong
     * 
     * @throws Exception
     */
    @Test
    public void testLoginFailedByWrongUsername() throws Exception {
	mockMvc.perform( post( "/login" ).param( "username", "test" ).param( "password", "123" ) ).andExpect( status().is( 401 ) );
    }
    
    /**
     * Test token when login successfully
     * 
     * @throws Exception
     */
    @Test
    public void testTokenWhenLoginSuccessfully() throws Exception {
	MvcResult response = mockMvc.perform( post( "/login" ).param( "username", "admin" ).param( "password", "admin" ) ).andExpect( status().is( 200 ) ).andReturn();

	String result = response.getResponse().getContentAsString();
	UserTokenState userTokenState = mapper.readValue( result, UserTokenState.class );
	String username = tokenProvider.getUsername( userTokenState.getToken() );
	assertEquals( "admin", username );
    }

    /**
     * test get authorities
     * 
     * @throws Exception
     */
    @Test
    public void testGetAuthorities() throws Exception {
	MvcResult response = mockMvc.perform( post( "/login" ).param( "username", "admin" ).param( "password", "admin" ) ).andExpect( status().is( 200 ) ).andReturn();
	UserTokenState userTokenState = mapper.readValue( response.getResponse().getContentAsString(), UserTokenState.class );
	String username = tokenProvider.getUsername( userTokenState.getToken() );
	User user = userRepo.findByUsername(username);
	Set<Role> roles = roleRepo.findByAuthority("ADMIN");
	assertEquals( roles.size(), user.getAuthorities().size() );
	assertTrue( user.getAuthorities().containsAll(roles));

    }
}
