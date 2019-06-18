package com.fsoft.libms;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsoft.libms.security.token.UserTokenState;

@RunWith( SpringJUnit4ClassRunner.class )
@SpringBootTest( classes = LibMsApplication.class )
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles( "test" )
@TestPropertySource( locations = "classpath:application-test.properties" )
public abstract class BaseTestCase {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper mapper;
    public static final String HEADER_STRING = "Authorization";

    protected byte[] loadFile( String fileName ) throws IOException {

	//Get file from resources folder
	ClassLoader classLoader = getClass().getClassLoader();
	Path path = Paths.get( classLoader.getResource( fileName ).getFile() );
	byte[] content = Files.readAllBytes( path );
	return content;

    }
    
    /**
     * Convert object to json
     */
    public String asJsonString( final Object obj ) {
	try {
	    return new ObjectMapper().writeValueAsString( obj );
	} catch ( Exception e ) {
	    throw new RuntimeException( e );
	}
    }
    
    /**
     * get path of file resources
     * 
     * @param fileName
     * @return
     * @throws URISyntaxException
     */
    public Path getPath( String pathName ) throws URISyntaxException {
//    	ClassLoader classLoader = getClass().getClassLoader();
//    	return Paths.get( classLoader.getResource( pathName ).toURI() );
    	return Paths.get(pathName).toAbsolutePath().normalize();
    }

    /**
     * get token of user with role is admin
     */
    public String tokenAdmin() throws Exception {
	MvcResult response = mockMvc.perform( post( "/login" ).param( "username", "admin" ).param( "password", "admin" ) ).andExpect( status().is( 200 ) ).andReturn();
	UserTokenState userTokenState = mapper.readValue( response.getResponse().getContentAsString(), UserTokenState.class );
	return userTokenState.getToken();
    }

    /**
     * get token of user with role is user
     */
    public String tokenUser() throws Exception {
	MvcResult response = mockMvc.perform( post( "/login" ).param( "username", "user" ).param( "password", "test" ) ).andExpect( status().is( 200 ) ).andReturn();
	UserTokenState userTokenState = mapper.readValue( response.getResponse().getContentAsString(), UserTokenState.class );
	return userTokenState.getToken();
    }
}
