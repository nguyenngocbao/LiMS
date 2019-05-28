package com.fsoft.libms.security.handler;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsoft.libms.security.token.TokenProvider;
import com.fsoft.libms.security.token.UserTokenState;

/**
 * 
 * This purpose of RestAuthenticationSuccessHandler to custom when login success
 *
 */
@Component
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess( HttpServletRequest request, HttpServletResponse response, Authentication authentication ) throws ServletException, IOException {
	clearAuthenticationAttributes( request );
	String generatedToken = tokenProvider.generateToken( authentication.getName() );
	UserTokenState userTokenState = new UserTokenState( generatedToken, tokenProvider.getExpiration( generatedToken ), TimeUnit.DAYS.toMillis( tokenProvider.getTokenExpiration() ) );
	String tokenResponse = objectMapper.writeValueAsString( userTokenState );
	response.setContentType( "application/json" );
	response.getWriter().write( tokenResponse );
    }
}
