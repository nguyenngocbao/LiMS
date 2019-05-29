package com.fsoft.libms.service;

import java.util.List;

import com.fsoft.libms.exception.LibMsException;
import com.fsoft.libms.model.Role;

/**
 * Role management service
 *
 */
public interface IRoleMgmtService {
    /**
     * createRole to insert role into table
     * 
     * @param role
     * @throws CodeFightException
     */
    public void createRole( String role ) throws LibMsException;

    /**
     * getListRoles to get list roles from table
     * 
     * @return
     * @throws CodeFightException
     */
    public List<Role> getRoles() throws LibMsException;

    /**
     * check role existing
     * 
     * @param role
     * @return true if the role exists, false otherwise
     * @throws CodeFightException
     */
    public boolean isRoleExisting( String role ) throws LibMsException;
}
