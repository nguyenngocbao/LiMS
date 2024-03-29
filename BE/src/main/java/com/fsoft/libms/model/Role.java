package com.fsoft.libms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Role implements GrantedAuthority
 */
@Entity
@Table( name = "role" )
public class Role extends AbstractPersistable<Long> implements GrantedAuthority {

    private static final long serialVersionUID = 1L;
    @Column (unique = true)
    private String authority;

    public Role() {
    }

    @JsonCreator
    public Role( @JsonProperty( ) String authority ) {
	this.authority = authority;
    }

    public void setAuthority( String authority ) {
	this.authority = authority;
    }

    @Override
    public String getAuthority() {
	return String.format( "ROLE_%s", authority );
    }

    @Override
    public String toString() {
	return authority;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((authority == null) ? 0 : authority.hashCode());
	return result;
    }

    @Override
    public boolean equals( Object obj ) {
	if ( this == obj )
	    return true;
	if ( obj == null )
	    return false;
	if ( getClass() != obj.getClass() )
	    return false;
	Role other = (Role) obj;
	if ( authority == null ) {
	    if ( other.authority != null )
		return false;
	} else if ( !authority.equals( other.authority ) )
	    return false;
	return true;
    }
}
