package com.fsoft.libms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fsoft.libms.model.Role;


/**
 * CRUD operation for ROLE
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("select r from Role r where r.authority = ?1")
    List<Role> findByAuthority(String authority);
}
