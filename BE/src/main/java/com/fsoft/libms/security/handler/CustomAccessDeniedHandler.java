package com.fsoft.libms.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * 
 * CustomAccessDeniedHandler Custom message when user do not have permission to
 * access
 *
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle( HttpServletRequest request, HttpServletResponse response, AccessDeniedException arg2 ) throws IOException, ServletException {
	response.sendError( HttpServletResponse.SC_FORBIDDEN, "Permission denied" );
    }

}
