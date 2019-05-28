package com.fsoft.libms.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fsoft.libms.exception.LibMsException;
import com.fsoft.libms.model.Role;
import com.fsoft.libms.model.Roles;
import com.fsoft.libms.model.User;
import com.fsoft.libms.repository.RoleRepository;
import com.fsoft.libms.repository.UserRepository;
import com.fsoft.libms.security.token.JWTBasedAuthentication;
import com.fsoft.libms.security.token.TokenProvider;
import com.fsoft.libms.service.IUserMgmtService;


/**
 * User mgmt service impl which is used by controller to retrieve data layer
 */
@Service
@Transactional
public class DefaultUserMgmtService implements IUserMgmtService {
    protected static final Logger LOGGER = LoggerFactory.getLogger( DefaultUserMgmtService.class );
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    TokenProvider tokenProvider;

    private static final String USER_EXIST = "Username already exists";
    private static final String INVALID_ROLE = "Invalid role (Valid roles: %s)";
    private static final String USER_NOT_FOUND = "Username '%s' not found";
    private static final String WRONG_PASSWORD = "Wrong password";
    private static final String LENG_USERNAME_INVALID = " Username must be at least 3 characters long and smaller 20 characters long";
    private static final String DEFAULT_IMAGE = "avatardefault.png";
    private static final String HOSTNAME = "webmail.tma.com.vn";
    private static final String SUBJECT = "Codefight Forget password";
    private static final String MAIL_USER = "intern.nhanh3@tma.com.vn";
    private static final String MAIL_PASSWORD = "12345678x@X";
    private static final String DEFAULT_IMAGES = "avatardefault.png";
    private static final String PASSWORD_NOT_MATCHING = "password is not matching";
    private static final String FORMAT_EMAIL = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@\\s+";
    private static final String INVALID_EMAIL = "Invalid email";
    private static final String NOT_EXIST_EMAIL = "Not exists email on system";
    private static final String UPCASE = "QWERTYUIOPASDFGHJKLZXCVBNM";
    private static final String NUMBER = "1234567890";
    private static final String MESSAGE_USER = "Your username is: ";
    private static final String MESSAGE_PASSWORD = "\nYour password auto reset is: ";
    private static final String ERROR = "ERROR!";
    private static final String SHORT_PASSWORD = "Password is too short";
    private static final String INVALID_USER = "Invalid user";

    /**
     * 
     * Create a new user
     *
     * @param user
     * @throws LibMsException
     */
    @Override
    public void createUser( User user ) throws LibMsException {
	if ( user.getPassword() == null || user.getPassword().isEmpty() )
	    throw new LibMsException( WRONG_PASSWORD );
	//username length > 3 and <20 character
	if ( user.getUsername().length() > 3 && user.getUsername().length() < 20 ) {
	    User addingUser = userRepo.findByUsername( user.getUsername() );
	    if ( addingUser != null ) {
		LOGGER.info( "Failed to create user with reason: " + USER_EXIST );
		throw new LibMsException( USER_EXIST );
	    }
	    if ( !user.getAuthorities().isEmpty() && !roleRepo.findAll().containsAll( user.getAuthorities().stream().collect( Collectors.toList() ) ) )
		throw new LibMsException( String.format( INVALID_ROLE, user.getAuthorities() ) );
	} else
	    throw new LibMsException( LENG_USERNAME_INVALID );
	//password lenght >6 character
	if ( user.getPassword().length() >= 6 ) {
	    user.setPassword( passwordEncoder.encode( user.getPassword() ) );
	} else
	    throw new LibMsException( SHORT_PASSWORD );

	//Set default some properties for user
	user.setAccountNonExpired( true );
	user.setAccountNonLocked( true );
	user.setEnabled( true );
	user.setCredentialsNonExpired( true );
	//set default image is avatardefault.png
	user.setPathImages( DEFAULT_IMAGE );

	User addEmail = userRepo.findByEmail( user.getEmail() );
	if ( addEmail != null ) {
	    LOGGER.info( "Failed to create user with reason: " + "Email is Exist" );
	    throw new LibMsException( "Email is Exist" );
	}
	userRepo.save( user );
    }
    
    public boolean isValid( String email ) throws LibMsException {
	String emailRegex = FORMAT_EMAIL;
	Pattern pat = Pattern.compile( emailRegex );
	if ( !pat.matcher( email ).matches() )
	    throw new LibMsException( INVALID_EMAIL );

	return true;
    }

    /**
     * Delete user
     *
     * @param username
     * @throws LibMsException
     */
    @Override
    public void deleteUser( String username ) throws LibMsException {
	userRepo.deleteByUsername( username );
    }

    /**
     * Edit input user
     *
     * @param user
     * @throws LibMsException
     */
    @Override
    public void editUser( User user ) throws LibMsException {
	User edittingUser = userRepo.getOne( user.getId() );
	if ( edittingUser == null )
	    throw new LibMsException( USER_NOT_FOUND );
	if ( !user.getAuthorities().isEmpty() && !roleRepo.findAll().containsAll( user.getAuthorities().stream().collect( Collectors.toList() ) ) )
	    throw new LibMsException( String.format( INVALID_ROLE, user.getAuthorities() ) );
	// need encode password before updating
	if ( user.getPassword() == null || user.getPassword().isEmpty() )
	    edittingUser.setPassword( passwordEncoder.encode( user.getPassword() ) );
	edittingUser.setUser( edittingUser );
	userRepo.save( edittingUser );
    }

    @Override
    public User findUserByName( String username ) throws LibMsException {
	User foundUser = userRepo.findByUsername( username );
	if ( foundUser == null )
	    throw new LibMsException( USER_NOT_FOUND );
	return foundUser;
    }

    /**
     * Get user details as string
     *
     * @param username
     * @return
     * @throws LibMsException
     */
    @Override
    public User getUserDetails( String username ) throws LibMsException {
	User user = userRepo.findByUsername( username );
	if ( user.getPathImages().equals( "" ) ) {
	    user.setPathImages( DEFAULT_IMAGES );
	}
	return user;
    }

    /**
     * update password
     * 
     * @param oldPasswordScreen
     *            and newPassword
     * @throws LibMsException
     */
    @Override
    public void updatePassword( String username, String oldPasswordScreen, String newPassword ) throws LibMsException {
	username = tokenProvider.getAuthToken().getName();
	User user = findUserByName( username );
	String oldPassword = user.getPassword();
	if ( !passwordEncoder.matches( oldPasswordScreen, oldPassword ) )
	    throw new LibMsException( PASSWORD_NOT_MATCHING );
	else if ( newPassword.length() < 6 )
	    throw new LibMsException( "password is very short" );
	else
	    user.setPassword( passwordEncoder.encode( newPassword ) );
	userRepo.save( user );
    }



    @Override
    public boolean isAdminOrEdit() {
	JWTBasedAuthentication jwtAu = tokenProvider.getAuthToken();
	if ( jwtAu == null )
	    return false;
	Collection<GrantedAuthority> listRole = tokenProvider.getAuthToken().getAuthorities();
	List<GrantedAuthority> roles = new ArrayList<>();
	roles.add( new Role( Roles.ADMIN ) );
	roles.add( new Role( Roles.EDIT ) );
	for ( GrantedAuthority role : listRole ) {
	    if ( role.getAuthority().equals( Roles.ADMIN ) || role.getAuthority().equals( Roles.EDIT ) )
		return true;
	}
	return false;
    }



}
