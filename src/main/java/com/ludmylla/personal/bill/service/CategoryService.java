package com.ludmylla.personal.bill.service;

import java.util.List;

import com.ludmylla.personal.bill.model.Category;

public interface CategoryService {

	Long save(Category category);

	List<Category> listAll();

	Category findByName(String name);

	List<Category> mostUsedCategory();
	
	void validIfCategoryIsNull(Category category);

	Long update(Category category);

	void delete(Long id);

}
