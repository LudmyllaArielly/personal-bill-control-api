package com.ludmylla.personal.bill.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ludmylla.personal.bill.model.Category;
import com.ludmylla.personal.bill.model.dto.CategoryCreateAndListAllDto;
import com.ludmylla.personal.bill.model.dto.CategoryUpdateDto;

@Mapper(uses = { BillMapper.class })
public interface CategoryMapper {

	CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

	@Mapping(target = "id", ignore = true)
	Category toCategory(CategoryCreateAndListAllDto source);

	Category toCategory(CategoryUpdateDto source);

	List<CategoryCreateAndListAllDto> dtoCategoryCreateAndListAllDto(List<Category> source);

}
