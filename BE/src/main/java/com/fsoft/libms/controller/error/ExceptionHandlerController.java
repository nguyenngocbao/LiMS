package com.fsoft.libms.controller.error;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fsoft.libms.exception.LibMsException;


/**
 * 
 * ExceptionHandlingController Custom error message handling for REST API
 *
 */
@ControllerAdvice
@ResponseBody
public class ExceptionHandlerController {
    /**
     * 
     * @param e
     * @param response
     * @return Handle Bad Request 
     */
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    // 400
    public ResponseEntity<String> handleBadRequest( LibMsException e ) {
	return new ResponseEntity<>( e.getMessage(), HttpStatus.BAD_REQUEST );
    }

    /**
     * 
     * @param e
     * @param response
     * @return Handle Bad Request 
     */
    @ExceptionHandler( LibMsException.class )
    public ResponseEntity<String> handleCodeFightException( LibMsException e ) {
	return new ResponseEntity<>( e.getMessage(), HttpStatus.BAD_REQUEST );
    }

    /**
     * 
     * @param e
     * @param response
     * @return Handle Bad Request MethodArgumentNotValidExceptions
     */

    @ResponseStatus( HttpStatus.BAD_REQUEST )
    // 400
    @ExceptionHandler( MethodArgumentNotValidException.class )
    public ResponseEntity<String> handleBadRequestNull( MethodArgumentNotValidException e ) {
	StringBuilder errors = new StringBuilder();
	List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
	for ( FieldError fieldError : fieldErrors ) {
	    String error = fieldError.getDefaultMessage() + "\r\n";
	    errors.append( error );
	}
	return new ResponseEntity<>( errors.toString(), HttpStatus.BAD_REQUEST );
    }

    /**
     * 
     * @param e
     * @param response
     * @return Handle Exceptions
     */

    @ExceptionHandler( TimeoutException.class )
    public ResponseEntity<String> handleTimeOutError( Exception e ) {
	return new ResponseEntity<>( "Request Timeout", HttpStatus.INTERNAL_SERVER_ERROR );
    }

    /**
     * 
     * @param e
     * @param response
     * @return Handle Exceptions
     */

    @ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
    // 500
    @ExceptionHandler( Exception.class )
    public ResponseEntity<String> handleGeneralError( Exception e ) {
	return new ResponseEntity<>( e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR );
    }

    /**
     * 
     * @param e
     * @return
     */
    @ResponseStatus( HttpStatus.FORBIDDEN )
    // 403
    @ExceptionHandler( AccessDeniedException.class )
    public ResponseEntity<String> handleAccessDeniedError( AccessDeniedException e ) {
	return new ResponseEntity<>( "Permission denied \r\n", HttpStatus.FORBIDDEN );
    }


    
}
