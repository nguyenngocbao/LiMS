package com.fsoft.libms.security.token;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 
 * The purpose of TokenAuthenticationFilter class is to test the JWT signature.
 * Get user information from JWT
 *
 */
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger( JWTAuthenticationFilter.class );

    @Autowired
    @Qualifier( "customUserDetailsService" )
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenProvider tokenProvider;

    /**
     * get token from request
     * 
     * @param request
     * @return
     */
    private String getToken( HttpServletRequest request ) {
	String authHeader = request.getHeader( TokenProvider.HEADER_STRING );
	if ( authHeader != null ) {// && authHeader.startsWith( TokenProvider.TOKEN_PREFIX ) ) {
	    return authHeader;//.replace( TokenProvider.TOKEN_PREFIX, "" );
	}
	return null;
    }

    @Override
    public void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain chain ) throws IOException, ServletException {
	String error = "";
	String sentToken = getToken( request );
	if ( sentToken != null && tokenProvider.validateToken( sentToken ) ) {
	    // Get username from token
	    String username = tokenProvider.getUsername( sentToken );
	    if ( username != null ) {
		// Get user
		UserDetails userDetails = userDetailsService.loadUserByUsername( username );
		// Create authentication

		// this one doesn't work - need to manually reset token or gen new token
		//		tokenProvider.updateExpiration( sentToken );
		JWTBasedAuthentication authToken = new JWTBasedAuthentication( userDetails );
		authToken.setToken( sentToken );
		SecurityContextHolder.getContext().setAuthentication( authToken );
	    } else {
		error = "Username from token can't be found.";
		LOGGER.error( error );
	    }
	} else {
	    error = "Authentication failed - no Bearer token provided.";
	    // LOGGER.error( error );

	}
	if ( !error.isEmpty() ) {
	    SecurityContextHolder.getContext().setAuthentication( null );
	}
	chain.doFilter( request, response );
    }
}
