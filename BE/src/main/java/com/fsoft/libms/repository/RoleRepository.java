package com.fsoft.libms.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsoft.libms.model.Role;


/**
 * CRUD operation for ROLE
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Set<Role> findByAuthority(String authority);
}
