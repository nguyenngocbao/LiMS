package com.fsoft.libms.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsoft.libms.exception.LibMsException;
import com.fsoft.libms.model.Role;
import com.fsoft.libms.model.Roles;
import com.fsoft.libms.model.User;
import com.fsoft.libms.repository.RoleRepository;
import com.fsoft.libms.repository.UserRepository;
import com.fsoft.libms.security.token.JWTBasedAuthentication;
import com.fsoft.libms.security.token.TokenProvider;
import com.fsoft.libms.service.IUploadImageService;
import com.fsoft.libms.service.IUserMgmtService;

/**
 * User mgmt service impl which is used by controller to retrieve data layer
 */
@Service
@Transactional
public class DefaultUserMgmtService implements IUserMgmtService {
	protected static final Logger LOGGER = LoggerFactory.getLogger(DefaultUserMgmtService.class);
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	TokenProvider tokenProvider;

	@Autowired
	private ObjectMapper objMapper;

	@Autowired
	private IUploadImageService uploadFile;

	private static final String USER_EXIST = "Username already exists";
	private static final String USER_NOT_FOUND = "Username '%s' not found";
	private static final String WRONG_PASSWORD = "Wrong password";
	private static final String LENG_USERNAME_INVALID = " Username must be at least 3 characters long and smaller 20 characters long";
	private static final String DEFAULT_IMAGES = "avatardefault.png";
	private static final String FORMAT_EMAIL = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@\\S+";
	private static final String INVALID_EMAIL = "Invalid email";
	private static final String SHORT_PASSWORD = "Password is too short";
	private static final String OLD_PASSWORD = "/oldPassword";
	private static final String NEW_PASSWORD = "/newPassword";
	private static final String RETYPE_PASSWORD = "/retypePassword";
	private static final String PASSWORD = "/password";
	private static final String USERNAME = "/username";
	private static final String EMAIL = "/email";
	private static final String FULL_NAME = "/fullName";
	private static final String ROLE = "/role";

	/**
	 * 
	 * Create a new user
	 *
	 * @param user
	 * @throws LibMsException
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	@Override
	public void createUser(String data, MultipartFile avatar)
			throws LibMsException, JsonProcessingException, IOException {
		User user = new User();
		JsonNode node = objMapper.readTree(data);
		JsonNode password = node.at(PASSWORD);
		JsonNode retypePassword = node.at(RETYPE_PASSWORD);
		JsonNode email = node.at(EMAIL);
		JsonNode fullName = node.at(FULL_NAME);
		JsonNode username = node.at(USERNAME);
		if (username.isMissingNode()) {
			throw new LibMsException("Username is required");
		}
		if (password.isMissingNode()) {
			throw new LibMsException("Password is required");
		}
		if (retypePassword.isMissingNode()) {
			throw new LibMsException("Retype Password is required");
		}
		if (fullName.isMissingNode()) {
			throw new LibMsException("Full name is required");
		}
		if (email.isMissingNode()) {
			throw new LibMsException("Email is required");
		}
		if (!password.asText().equals(retypePassword.asText())) {
			throw new LibMsException("Password and retype password are not matched");
		}
		user.setUsername(username.asText());
		user.setPassword(password.asText());
		user.setEmail(email.asText());
		user.setFullName(fullName.asText());

		if (user.getPassword() == null || user.getPassword().isEmpty())
			throw new LibMsException(WRONG_PASSWORD);
		// username length > 3 and <20 character
		isValid(user.getEmail());
		if (user.getUsername().length() > 3 && user.getUsername().length() < 20) {
			User addingUser = userRepo.findByUsername(user.getUsername());
			if (addingUser != null) {
				LOGGER.info("Failed to create user with reason: " + USER_EXIST);
				throw new LibMsException(USER_EXIST);
			}
			// password lenght >6 character
			if (user.getPassword().length() >= 6) {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
			} else
				throw new LibMsException(SHORT_PASSWORD);

			// Set default some properties for user
			user.setAccountNonExpired(true);
			user.setAccountNonLocked(true);
			user.setEnabled(true);
			user.setCredentialsNonExpired(true);

			User addEmail = userRepo.findByEmail(user.getEmail());
			if (addEmail != null) {
				LOGGER.info("Failed to create user with reason: " + "Email is Exist");
				throw new LibMsException("Email is Exist");
			}
			String fileName = avatar.getOriginalFilename();
			int index = fileName.lastIndexOf(".");
			fileName = user.getUsername() + fileName.substring(index);
			uploadFile.storeFileAvatar(avatar, fileName);
			// set default image is avatardefault.png
			user.setPathImages(String.format("%s%s", "/api/upload/user/", fileName));

			if (tokenProvider.getAuthToken() != null && tokenProvider.getAuthToken().isAuthenticated()) {
				if (isAdmin()) {
					user.setAuthorities(user.getAuthorities());
					JsonNode role = node.at(ROLE);
					String roleString = role.isMissingNode() ? "USER" : role.asText();
					Set<Role> roles = roleRepo.findByAuthority(roleString);
					if (roles.size() == 0) {
						throw new LibMsException("Role is not supported");
					}
					user.setAuthorities(roles);
				}
			} else {
				Set<Role> roles = roleRepo.findByAuthority("USER");
				user.setAuthorities(roles);
			}
			userRepo.save(user);

			// if (!user.getAuthorities().isEmpty()
			// &&
			// !roleRepo.findAll().containsAll(user.getAuthorities().stream().collect(Collectors.toList())))
			// throw new LibMsException(String.format(INVALID_ROLE, user.getAuthorities()));
		} else {
			throw new LibMsException(LENG_USERNAME_INVALID);
		}

	}

	public boolean isValid(String email) throws LibMsException {
		String emailRegex = FORMAT_EMAIL;
		Pattern pat = Pattern.compile(emailRegex);
		if (!pat.matcher(email).matches())
			throw new LibMsException(INVALID_EMAIL);

		return true;
	}

	/**
	 * Delete user
	 *
	 * @param username
	 * @throws LibMsException
	 */
	@Override
	public void deleteUser(String username) throws LibMsException {
		userRepo.deleteByUsername(username);
	}

	/**
	 * Edit input user
	 *
	 * @param user
	 * @throws LibMsException
	 */
	@Override
	public void editUser(String data, MultipartFile avatar) throws LibMsException, JsonProcessingException, IOException {
		
		String userCurrent = tokenProvider.getAuthToken().getName();
		User edittingUser = userRepo.findByUsername(userCurrent);
		if (edittingUser == null)
			throw new LibMsException(USER_NOT_FOUND);
		JsonNode node = objMapper.readTree(data);
		JsonNode email = node.at(EMAIL);
		JsonNode fullName = node.at(FULL_NAME);
		if (!fullName.isMissingNode()) {
			edittingUser.setFullName(fullName.asText());
		}
		if (!email.isMissingNode()) {
			isValid(email.asText());
			User addEmail = userRepo.findByEmail(email.asText());
			if (addEmail != null) {
				LOGGER.info("Failed to create user with reason: " + "Email is Exist");
				throw new LibMsException("Email is Exist");
			}
			edittingUser.setEmail(email.asText());
		}
		String fileName = avatar.getOriginalFilename();
		int index = fileName.lastIndexOf(".");
		fileName = edittingUser.getUsername() + fileName.substring(index);
		uploadFile.storeFileAvatar(avatar, fileName);
		// set default image is avatardefault.png
		edittingUser.setPathImages(String.format("%s%s", "/api/upload/user/", fileName));
		edittingUser.setUser(edittingUser);
		userRepo.save(edittingUser);
	}

	public void changePassword(String data) throws JsonProcessingException, IOException, LibMsException {
		JsonNode node = objMapper.readTree(data);
		JsonNode newPassword = node.at(NEW_PASSWORD);
		JsonNode retypePassword = node.at(RETYPE_PASSWORD);
		JsonNode oldPassword = node.at(OLD_PASSWORD);
		if (newPassword.isMissingNode() || retypePassword.isMissingNode() || oldPassword.isMissingNode()) {
			throw new LibMsException("New password, retype password and old password are required");
		}
		if (!newPassword.asText().equals(retypePassword.asText())) {
			throw new LibMsException("New password and retype password are not matched");
		}
		if (!passwordEncoder.matches(oldPassword.asText(), tokenProvider.getAuthToken().getPrincipal().getPassword())) {
			throw new LibMsException("Old password is invalid");
		}

		if (newPassword.asText().length() < 6)
			throw new LibMsException("password is very short");

		User user = userRepo.findByUsername(tokenProvider.getAuthToken().getName());
		user.setPassword(passwordEncoder.encode(newPassword.asText()));
		userRepo.save(user);

	}

	@Override
	public User findUserByName(String username) throws LibMsException {
		User foundUser = userRepo.findByUsername(username);
		if (foundUser == null)
			throw new LibMsException(USER_NOT_FOUND);
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
	public User getUserDetails(String username) throws LibMsException {
		User user = userRepo.findByUsername(username);
		if (user.getPathImages().equals("")) {
			user.setPathImages(DEFAULT_IMAGES);
		}
		return user;
	}

	@Override
	public boolean isAdmin() {
		JWTBasedAuthentication jwtAu = tokenProvider.getAuthToken();
		if (jwtAu == null)
			return false;
		Collection<GrantedAuthority> listRole = tokenProvider.getAuthToken().getAuthorities();
		List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new Role(Roles.ADMIN));
		for (GrantedAuthority role : listRole) {
			return role.getAuthority().equals(Roles.ADMIN);
		}
		return false;
	}

	public Page<User> getList(Pageable pageable) {
		return userRepo.findAll(pageable);
	}

}
