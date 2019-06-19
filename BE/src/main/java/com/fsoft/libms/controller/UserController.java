package com.fsoft.libms.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fsoft.libms.exception.LibMsException;
import com.fsoft.libms.model.Roles;
import com.fsoft.libms.model.User;
import com.fsoft.libms.service.IUserMgmtService;

@RestController
@RequestMapping("/api/user")
public class UserController extends AbstractController {

	@Autowired
	private IUserMgmtService userService;

	@CrossOrigin
	@PostMapping(value = "/create")
	public void addUser(@RequestParam String data, @RequestParam("file") MultipartFile file)
			throws LibMsException, JsonParseException, JsonMappingException, IOException {
		userService.createUser(data, file);
	}

	@Secured(Roles.ADMIN)
	@PostMapping(value = "/admin/create")
	public void addUserByAdmin(@RequestBody String data)
			throws LibMsException, JsonParseException, JsonMappingException, IOException {
		userService.createUserByAdmin(data);
	}
	
	@PutMapping()
	public void editUser(@RequestParam String data, @RequestParam("file") MultipartFile file)
			throws JsonParseException, JsonMappingException, IOException, LibMsException {

		userService.editUser(data, file);
	}
	
	@Secured(Roles.ADMIN)
	@DeleteMapping(value="/{username}")
	public void deleteUser( @PathVariable String username ) throws LibMsException {
		userService.deleteUser(username);
	}
	
	@PutMapping(value="/change-password")
	public void forgetPassword(@RequestBody String data) throws JsonProcessingException, IOException, LibMsException {
		userService.changePassword(data);
	}
	@CrossOrigin
	@GetMapping(value="/list")
	public Page<User> getUsers(Pageable pageable) {
		return userService.getList(pageable);
	}
	
	@CrossOrigin
	@GetMapping(value="/detail/{username}")
	public User getUser( @PathVariable String username ) throws LibMsException {
		return userService.findUserByName(username);
	}
	
	@GetMapping(value = "/isAdmin")
	public boolean isAdmin() {
		return userService.isAdmin();
	}
	
	
}
