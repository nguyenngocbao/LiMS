package com.fsoft.libms.security.token;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * 
 * Create JWT and JWT utility functionality
 *
 */
@Component
public class TokenProvider {

    @Value( "${token.expiration:2}" )
    private long tokenExpiration;

    public static final String SECRET = "ThisIsASecret";
    public static final String HEADER_STRING = "Authorization";
    public static final SimpleDateFormat SDF = new SimpleDateFormat( "YYYY-MM-DD HH:MM:SS" );

    /**
     * create token from username
     * 
     * @param username
     * @return
     */
    public String generateToken( String username ) {
	return Jwts.builder().setSubject( username ).setExpiration( new Date( System.currentTimeMillis() + TimeUnit.DAYS.toMillis( tokenExpiration ) ) ).signWith( SignatureAlgorithm.HS512, SECRET ).compact();
    }

    /**
     * Get username from token
     * 
     * @param authToken
     * @return
     */
    public String getUsername( String authToken ) {
	return Jwts.parser().setSigningKey( SECRET ).parseClaimsJws( authToken ).getBody().getSubject();
    }

    /**
     * Update expiration date for valid request
     * 
     * @param authToken
     */
    public void updateExpiration( String authToken ) {
	Jwts.parser().setSigningKey( SECRET ).parseClaimsJws( authToken ).getBody().setExpiration( new Date( System.currentTimeMillis() + TimeUnit.DAYS.toMillis( tokenExpiration ) ) );
    }

    /**
     * Get token expiration date details
     * 
     * @param authToken
     * @return
     */
    public Date getExpiration( String authToken ) {
	return Jwts.parser().setSigningKey( SECRET ).parseClaimsJws( authToken ).getBody().getExpiration();
    }

    /**
     * Get token expiration in milisecond
     * 
     * @return
     */
    public long getTokenExpiration() {
	return tokenExpiration;
    }

    /**
     * Validate input token
     * 
     * @param authToken
     * @return
     */
    public boolean validateToken( String authToken ) {
	try {
	    Jwts.parser().setSigningKey( SECRET ).parseClaimsJws( authToken );
	    return true;
	} catch ( SignatureException e ) {
	    System.out.println( "Invalid JWT signature." );
	    //            log.info("Invalid JWT signature.");
	    //            log.trace("Invalid JWT signature trace: {}", e);
	} catch ( MalformedJwtException e ) {
	    System.out.println( "Invalid JWT token." );
	    //            log.info("Invalid JWT token.");
	    //            log.trace("Invalid JWT token trace: {}", e);
	} catch ( ExpiredJwtException e ) {
	    System.out.println( "Expired JWT token." );
	    //            log.info("Expired JWT token.");
	    //            log.trace("Expired JWT token trace: {}", e);
	} catch ( UnsupportedJwtException e ) {
	    System.out.println( "Unsupported JWT token." );
	    //            log.info("Unsupported JWT token.");
	    //            log.trace("Unsupported JWT token trace: {}", e);
	} catch ( IllegalArgumentException e ) {
	    System.out.println( "JWT token compact of handler are invalid." );
	    //            log.info("JWT token compact of handler are invalid.");
	    //            log.trace("JWT token compact of handler are invalid trace: {}", e);
	}
	return false;
    }

    /**
     * Get authenticated token
     * 
     * @return
     */
    public JWTBasedAuthentication getAuthToken() {
	return (JWTBasedAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}
