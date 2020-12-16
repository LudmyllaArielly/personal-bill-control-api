package com.ludmylla.personal.bill.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ludmylla.personal.bill.model.Pay;
import com.ludmylla.personal.bill.model.dto.PayInsertAndListAllDto;
import com.ludmylla.personal.bill.model.dto.PayUpdateDto;

@Mapper
public interface PayMapper {
	
	PayMapper INSTANCE = Mappers.getMapper(PayMapper.class);
	
	@Mapping(target = "id", ignore = true)
	Pay toPayInsertAndListAllDto (PayInsertAndListAllDto source);
	
	PayInsertAndListAllDto dtoPayInsertAndListAllDto (Pay source);
	
	List<PayInsertAndListAllDto> dtoPayInsertAndListAllDto (List<Pay> source);
	
	Pay toPayUpdateDto (PayUpdateDto source);

}