package com.fsoft.libms.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.fsoft.libms.exception.LibMsException;
import com.fsoft.libms.model.Role;
import com.fsoft.libms.service.IRoleMgmtService;


/**
 * 
 * Role mgmt service impl which is used by controller to retrieve data layer
 *
 */
@Service
@Transactional
public class DefaultRoleMgmtService implements IRoleMgmtService {
    /**
     * Create user role
     * 
     * @param role
     * @throws LibMsException
     */
    @Override
    public void createRole( String role ) throws LibMsException {
	// TODO: Check if no existing; adding new one
    }

    @Override
    public List<Role> getRoles() throws LibMsException {
	// TODO Need implementing
	return null;
    }

    @Override
    public boolean isRoleExisting( String role ) throws LibMsException {
	// TODO Need implementing
	return false;
    }
}