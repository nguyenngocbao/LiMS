package com.fsoft.libms.exception;

/**
 * Basic exception for application
 */
public class LibMsException extends Exception {

    private static final long serialVersionUID = 1L;

    public LibMsException( String message ) {
	super( message );
    }

    public LibMsException( String message, Exception exception ) {
	super( message, exception );
    }

}