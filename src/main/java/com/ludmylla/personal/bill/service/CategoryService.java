package com.ludmylla.personal.bill.service;

import java.util.List;

import com.ludmylla.personal.bill.model.Category;

public interface CategoryService {

	Long save(Category category);
	
	List<Category> listAll();
	
	Long update(Category category);
	
	void delete(Long id);

	List<Category> findCategoryBill(List<Category> category);
	 
	Category findByName(String name);
	
	List<Category> mostUsedCategory();
	 

}
