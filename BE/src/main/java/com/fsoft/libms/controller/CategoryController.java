package com.fsoft.libms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsoft.libms.exception.LibMsException;
import com.fsoft.libms.model.Category;
import com.fsoft.libms.model.Roles;
import com.fsoft.libms.service.ICategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController extends AbstractController {

	@Autowired
	private ICategoryService categoryService;
	@CrossOrigin
	@Secured(Roles.ADMIN)
	@PostMapping()
	public Category addCategory( @RequestBody String data) throws LibMsException {
		return categoryService.addCategory(data);
	}
	@CrossOrigin
	@GetMapping()
	public List<Category> categories() {
		return categoryService.categories();
	}
	@Secured(Roles.ADMIN)
	@PutMapping(value = "/{id}")
	public Category editCategory( @PathVariable long id , @RequestBody String data) throws LibMsException {
		return categoryService.editCategory(data, id);
	}

	@DeleteMapping(value= "/{id}")
	public void deleteCategory(@PathVariable long id) throws LibMsException {
		categoryService.deleteCategory(id);
	}
	
	@GetMapping(value= "/{id}")
	public Category get(@PathVariable long id) {
		return categoryService.getCategoryById(id);
	}
	
}
