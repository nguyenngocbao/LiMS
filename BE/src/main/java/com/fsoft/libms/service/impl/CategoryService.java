package com.fsoft.libms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fsoft.libms.exception.LibMsException;
import com.fsoft.libms.model.Book;
import com.fsoft.libms.model.Category;
import com.fsoft.libms.repository.CategoryRepository;
import com.fsoft.libms.service.AbstractService;
import com.fsoft.libms.service.ICategoryService;
import com.fsoft.libms.service.IUploadImageService;

@Service
public class CategoryService extends AbstractService implements ICategoryService {
	@Autowired
	private CategoryRepository categoryRepo;
	@Autowired
	private IUploadImageService uploadImage;

	public Category addCategory(String data) throws LibMsException {
		Category cate = categoryRepo.findByName(data);
		if (cate != null) {
			throw new LibMsException(String.format("Danh mục %s đã tồn tại", data));
		}
		
		Category category = new Category();
		category.setName(data);
		return categoryRepo.saveAndFlush(category);
	}

	public Category editCategory(String data, long id) throws LibMsException {
		Category category = categoryRepo.findById(id);
		if (category != null) {
			category.setName(data);
			return categoryRepo.saveAndFlush(category);
		} else {
			throw new LibMsException("Category not found");
		}

	}

	public void deleteCategory(long id) throws LibMsException {
		Category category = categoryRepo.findById(id);
		if (category != null) {
			categoryRepo.delete(id);
			List<Book> books = category.getBooks();
			for (Book book : books) {
				if (book.getImage() != null) {
					uploadImage.deleteFile(book.getImage().replace("/api/upload/", ""));
				}
			}
		} else {
			throw new LibMsException("Category not found");
		}
	}

	public List<Category> categories() {
		return categoryRepo.findAll();
	}

	public Category getCategoryById(long id) {
		return categoryRepo.findById(id);
	}
}
