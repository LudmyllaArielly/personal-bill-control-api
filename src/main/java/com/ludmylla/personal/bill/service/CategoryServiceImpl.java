package com.ludmylla.personal.bill.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ludmylla.personal.bill.model.Category;
import com.ludmylla.personal.bill.repository.CategoryRepository;
import com.ludmylla.personal.bill.useful.Useful;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserService userService;

	@Transactional
	@Override
	public Long save(Category category) {
		validations(category);
		Category categoryCreate = categoryRepository.save(category);
		return categoryCreate.getId();
	}
	
	@Transactional
	@Override
	public List<Category> listAll() {
		List<Category> list = categoryRepository.findAll();
		return list;
	}

	@Modifying
	@Transactional
	@Override
	public Long update(Category category) {
		validationsIfCategoryExits(category.getId());
		validations(category);
		Category categoryUpdate = categoryRepository.save(category);
		return categoryUpdate.getId();
	}

	@Transactional
	@Override
	public void delete(Long id) {
		validationsIfCategoryExits(id);
		validationsDelete();
		Optional<Category> category = categoryRepository.findById(id);
		Category categories = category.get();
		categoryRepository.delete(categories);
	}

	private void validations(Category category) {
		validIfCategoryNameIsNull(category);
		validIfCategoryNameIsBlank(category);
		validTheQuantityOfLettersInCategory(category);
		validIfTheNameHasNumbersOrSpecialCharacters(category);
		validIfTokenIsNull();
		validUserAccess();
	}
	
	private void validationsDelete() {
		validIfTokenIsNull();
		validUserAccess();
	}
	
	private void validationsIfCategoryExits(Long id) {
		validIfCategoryExits(id);
	}

	private void validIfCategoryNameIsNull(Category category) {
		boolean isNameNull = category.getName() == null;
		if (isNameNull) {
			throw new IllegalArgumentException("Category name cannot be null!");
		}
	}

	private void validIfCategoryNameIsBlank(Category category) {
		boolean isNameBlank = category.getName().isBlank();
		if (isNameBlank) {
			throw new IllegalArgumentException("The category name cannot be empty!");
		}
	}

	private void validTheQuantityOfLettersInCategory(Category category) {
		String isNameMinAndMax = category.getName();
		if (isNameMinAndMax.length() < 3 || isNameMinAndMax.length() > 50) {
			throw new IllegalArgumentException("Category must have a minimum of 3 letters and a maximum of 50!");
		}
	}
	
	private void validIfCategoryExits(Long id) {
		Optional<Category> category = categoryRepository.findById(id);
		boolean isCategoryExists = category.isEmpty();
		if (isCategoryExists) {
			throw new IllegalArgumentException("Category does not exist!");
		}
	}

	private void validIfTheNameHasNumbersOrSpecialCharacters(Category category) {
		String hasNumbersOrSpecialCharactersInName = category.getName();
		Useful.validIfItHasNumbersOrSpecialCharacters(hasNumbersOrSpecialCharactersInName);
	}
	
	private void validIfTokenIsNull() {
		userService.validIfTokenIsNull();
	}
	
	private void validUserAccess() {
		userService.releasesAuthorizationForTheUser();
	}

}
