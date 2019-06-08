package com.fsoft.libms.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "user")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class User extends AbstractPersistable<Long> implements UserDetails {
	private static final long serialVersionUID = 1L;

	/**
	 * full name
	 */
	@Column
	@NotNull(message = "fullName not found")
	@NotEmpty(message = "fullName can not be empty")
	private String fullName;
	/**
	 * username the user's username
	 */
	@Column(unique = true)
	@NotNull(message = "Username not found")
	@NotEmpty(message = "Username can not be empty")
	private String username;

	@Column(unique = true)
	@NotNull(message = "email not found")
	@NotEmpty(message = "email can not be empty")
	private String email;
	/**
	 * password the user's password
	 */
	@Column
	@NotNull(message = "Password not found")
	@NotEmpty(message = "Password can not be empty")
	@JsonIgnore
	private String password;

	/**
	 * authorites list of user's roles
	 */
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	// @formatter:off
	@JoinTable(name = "user_roles", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", referencedColumnName = "id") })
	// @formatter:on
	private Set<Role> authorities;

	// TODO: following attributes are created to pass authenticate as now. Will
	// revise later to have correct configuration.
	/**
	 * Indicates whether the user's account has expired. An expired account cannot
	 * be authenticated
	 */
	private boolean accountNonExpired;
	/**
	 * Indicates whether the user is locked or unlocked. A locked user cannot be
	 * authenticated.
	 */
	private boolean accountNonLocked;
	/**
	 * Indicates whether the user's credentials (password) has expired. Expired
	 * credentials prevent authentication.
	 */
	private boolean credentialsNonExpired;
	/**
	 * Indicates whether the user is enabled or disabled. A disabled user cannot be
	 * authenticated.
	 */

	private boolean enabled;

	/**
	 * path images
	 */
	@Column(name = "path_images")
	private String pathImages;

	public String getPathImages() {
		return pathImages;
	}

	public void setPathImages(String pathImages) {
		this.pathImages = pathImages;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User() {

	}

	/**
	 * set user value based on input user
	 * 
	 * @param user
	 */
	public User(UserDetails user) {
		setUsername(user.getUsername());
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public String setPassword(String password) {
		return this.password = password;
	}

	@Override
	public Set<Role> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Role> authorities) {
		this.authorities = authorities;
	}

	/**
	 * @return true if the user's account is valid (ie non-expired), false if no
	 *         longer valid (ie expired)
	 */
	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	/**
	 * @return true if the user is not locked, false otherwise
	 */
	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	/**
	 * @return true if the user's credentials are valid (ie non-expired), false if
	 *         no longer valid (ie expired)
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	/**
	 * @return true if the user is enabled, false otherwise
	 */
	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorities == null) ? 0 : authorities.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	/**
	 * set user value based on input user; used for updating
	 * 
	 * @param user
	 */
	public void setUser(User user) {
		setAccountNonExpired(user.isAccountNonExpired());
		setAccountNonLocked(user.isAccountNonLocked());
		setCredentialsNonExpired(user.isCredentialsNonExpired());
		setEnabled(user.isEnabled());
	}

	// Get path image
	/*
	 * public String convertPathImage( ){ String res = this.pathImages; if ( res ==
	 * null ) res = CodeFightConstants.EMPTY; res = (res.trim().length() == 0 ?
	 * "/profile/avatar/avatardefault.png" : "/profile/avatar/" + res); return res;
	 * }
	 */
}