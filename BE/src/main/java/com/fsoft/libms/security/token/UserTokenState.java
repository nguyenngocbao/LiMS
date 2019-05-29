package com.fsoft.libms.security.token;

import java.util.Date;

/**
 * 
 * This is object that will be returned after successful login
 *
 */
public class UserTokenState {
    private String token;
    private long expires;
    private Date expireDate;

    public UserTokenState( String token, Date expireDate, long expires ) {
	this.token = token;
	this.setExpireDate( expireDate );
	this.expires = expires;
    }

    public UserTokenState( String token, long expires ) {
	this.token = token;
	this.expires = expires;
    }

    public UserTokenState() {
    }

    public String getToken() {
	return token;
    }

    public void setToken( String token ) {
	this.token = token;
    }

    public long getExpires() {
	return expires;
    }

    public void setExpires( long expires ) {
	this.expires = expires;
    }

    @Override
    public String toString() {
	return "UserTokenState [token=" + token + ", expires=" + expires + "/" + expireDate + "]";
    }

    public Date getExpireDate() {
	return expireDate;
    }

    public void setExpireDate( Date expireDate ) {
	this.expireDate = expireDate;
    }

}