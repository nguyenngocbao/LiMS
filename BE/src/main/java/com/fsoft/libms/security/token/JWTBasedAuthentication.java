package com.fsoft.libms.security.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 
 * TokenBasedAuthentication class will define authorities from the userDetails
 * object and these authorities will check later in WebSecurityConfig.
 *
 */
public class JWTBasedAuthentication extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 1L;
    private String token;
    private final UserDetails userDetails;

    public JWTBasedAuthentication( UserDetails userDetails ) {
	super( userDetails.getAuthorities() );
	this.userDetails = userDetails;
    }

    public String getToken() {
	return token;
    }

    public void setToken( String token ) {
	this.token = token;
    }

    @Override
    public boolean isAuthenticated() {
	return true;
    }

    @Override
    public Object getCredentials() {
	return token;
    }

    @Override
    public UserDetails getPrincipal() {
	return userDetails;
    }

}