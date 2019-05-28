package com.fsoft.libms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.fsoft.libms.service.AbstractService;
import com.fsoft.libms.service.IUserMgmtService;
import com.fsoft.libms.model.User;


@Component( "customUserDetailsService" )
public class CustomUserDetailsService extends AbstractService implements UserDetailsService {
    @Autowired
    private IUserMgmtService userService;

    /**
     * @param username
     *            the username identifying the user whose data is required.
     * @return UserDetails a fully populated user record
     * @throws UsernameNotFoundException
     *             if the user could not be found
     */
    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
	try {
	    // this user include role/grantedAuthority
	    User user = userService.findUserByName( username );
	    if ( user == null ) {
		throw new UsernameNotFoundException( "Username " + username + " not found" );
	    }
	    return user;
	} catch ( Exception e ) {
	    LOGGER.error( "Unable to load user. " + e.getMessage(), e );
	    throw new UsernameNotFoundException( "Could not load user information", e );
	}
    }
}
