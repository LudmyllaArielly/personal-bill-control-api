package com.ludmylla.personal.bill.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ludmylla.personal.bill.mapper.CategoryMapper;
import com.ludmylla.personal.bill.model.Category;
import com.ludmylla.personal.bill.model.dto.CategoryInsertAndListAllDto;
import com.ludmylla.personal.bill.model.dto.CategoryUpdateDto;
import com.ludmylla.personal.bill.service.CategoryService;

@RestController
public class CategoryResource {
	
	@Autowired
	private CategoryService categoryService;
	

	@PostMapping(path = "/categories")
	public ResponseEntity<String> createCategory(@Valid @RequestBody CategoryInsertAndListAllDto categoryInsertAndListAllDto){
		try {
			Category category = CategoryMapper.INSTANCE.toCategoryInsertAndListAllDto(categoryInsertAndListAllDto);
			
			categoryService.save(category);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(
					" Category successfully registered! ");
			
		}catch (DataIntegrityViolationException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(" Category failure already exists: " + ex.getMessage());
			
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Failed to create category: " + e.getMessage());
		}
	}
	
	@GetMapping(path = "/categories")
	public ResponseEntity<List<CategoryInsertAndListAllDto>> listAll(){
		try {
			List<Category> categories = categoryService.listAll();
			
			List<CategoryInsertAndListAllDto> categoryInsertAndListAllDto = CategoryMapper.INSTANCE
					.dtoCategoryInsertAndListAll(categories);
			
			return new ResponseEntity<List<CategoryInsertAndListAllDto>>(categoryInsertAndListAllDto,HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<List<CategoryInsertAndListAllDto>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(path = "/categories")
	public ResponseEntity<String> updateCategory(@Valid @RequestBody CategoryUpdateDto categoryUpdateDto){
		try {
			Category category = CategoryMapper.INSTANCE.toCategoryUpdateDto(categoryUpdateDto);
			
			categoryService.update(category);
			return ResponseEntity.status(HttpStatus.OK).body(" Category update successfully: ");
		
		}catch (DataIntegrityViolationException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(" Category failure already exists: " + ex.getMessage());
			
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Failed to update: " + e.getMessage());
		}
	}
	
	@DeleteMapping(path = "/categories")
	public ResponseEntity<String> deleteCategory(Long id){
		try {
			categoryService.delete(id);
			return ResponseEntity.status(HttpStatus.OK).body(" Category delete successfully! ");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Failed to delete: " + e.getMessage());
		}
	}
	
}
