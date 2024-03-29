package com.fsoft.libms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsoft.libms.model.User;


/**
 * CRUD operation for USER
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername( String username );

    public User findByEmail(String email);

    public void deleteByUsername( String username );
    
    
}
