package com.ludmylla.personal.bill.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ludmylla.personal.bill.model.Category;
import com.ludmylla.personal.bill.model.dto.CategoryInsertAndListAllDto;
import com.ludmylla.personal.bill.model.dto.CategoryUpdateDto;

@Mapper
public interface CategoryMapper {

	CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

	@Mapping(target = "id", ignore = true)
	Category toCategoryInsertAndListAllDto(CategoryInsertAndListAllDto source);

	Category toCategoryUpdateDto(CategoryUpdateDto source);
	
	List<CategoryInsertAndListAllDto> dtoCategoryInsertAndListAll (List<Category> source);

}
