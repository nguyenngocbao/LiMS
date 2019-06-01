package com.fsoft.libms.service;

import java.util.List;

import com.fsoft.libms.exception.LibMsException;
import com.fsoft.libms.model.Category;

public interface ICategoryService {
	public Category addCategory(String data);
	public Category editCategory(String data, long id) throws LibMsException;
	public void deleteCategory(long id) throws LibMsException;
	public List<Category> categories ();
	public Category getCategoryById(long id);
}
