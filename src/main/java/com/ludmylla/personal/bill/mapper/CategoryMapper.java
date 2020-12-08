package com.ludmylla.personal.bill.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ludmylla.personal.bill.model.Category;
import com.ludmylla.personal.bill.model.dto.CategoryInsertDto;
import com.ludmylla.personal.bill.model.dto.CategoryListAllDto;
import com.ludmylla.personal.bill.model.dto.CategoryUpdateDto;

@Mapper
public interface CategoryMapper {

	CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
	
	@Mapping(target = "id", ignore = true)
	Category toCategoryInsertDto (CategoryInsertDto source);
	
	Category toCategoryUpdateDto (CategoryUpdateDto source);
	
	@Mapping(target= "id", ignore = true)
	Category toCategoryListAllDto (CategoryListAllDto source);
	
	List<CategoryListAllDto> dtoCategoryListAllDto (List<Category> source);
	
	
}
