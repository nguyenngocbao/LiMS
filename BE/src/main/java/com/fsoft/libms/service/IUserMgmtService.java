package com.fsoft.libms.service;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
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
    public void createUser( String data, MultipartFile image ) throws LibMsException, JsonProcessingException, IOException;

    public void createUserByAdmin(String data) throws LibMsException, JsonProcessingException, IOException;
    /**
     *
     * @param user
     * @throws LibMsException
     *             editUser
     */
    public void editUser( String data, MultipartFile avatar ) throws LibMsException, JsonProcessingException, IOException;

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
    public User findUserByName( String username ) throws LibMsException;



    /**
     * check user has include Admin or edit role
     * 
     * @return
     */
    public boolean isAdmin();
    
    public void changePassword( String data ) throws JsonProcessingException, IOException, LibMsException;
    
    public Page<User> getList(Pageable pageable);
    
    public void forgetPassword(String data) throws LibMsException;



}
