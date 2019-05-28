package com.fsoft.libms.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * 
 * RestAuthenticationEntryPoint
 *
 */
@Component( "restAuthenticationEntryPoint" )
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence( HttpServletRequest request, HttpServletResponse response, AuthenticationException authenEx ) throws IOException, ServletException {
	response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Login failed!" );
    }
}