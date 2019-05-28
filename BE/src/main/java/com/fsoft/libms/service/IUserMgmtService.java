package com.fsoft.libms.service;

import com.fsoft.libms.exception.LibMsException;
import com.fsoft.libms.model.User;


/**
 * User management service
 *
 */
public interface IUserMgmtService {
    /**
     *
     * @param user
     * @throws LibMsException
     */
    public void createUser( User user ) throws LibMsException;

    /**
     *
     * @param user
     * @throws LibMsException
     *             editUser
     */
    public void editUser( User user ) throws LibMsException;

    /**
     *
     * @param username
     * @throws LibMsException
     *             deleteUser
     */
    public void deleteUser( String username ) throws LibMsException;

    /**
     * Get user details
     * 
     * @param username
     * @return
     * @throws LibMsException
     */
    public User getUserDetails( String username ) throws LibMsException;

    /**
     * Find user by name
     * 
     * @param username
     * @return
     * @throws LibMsException
     */
    User findUserByName( String username ) throws LibMsException;



    /**
     * check user has include Admin or edit role
     * 
     * @return
     */
    public boolean isAdminOrEdit();
    
    public void updatePassword( String username, String oldPasswordScreen, String newPassword ) throws LibMsException;



}
