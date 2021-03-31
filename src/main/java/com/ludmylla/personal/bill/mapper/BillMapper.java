package com.ludmylla.personal.bill.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.ludmylla.personal.bill.model.Bill;
import com.ludmylla.personal.bill.model.dto.BillCreateDto;

@Mapper(componentModel = "spring")
public interface BillMapper {

	BillMapper INSTANCE = Mappers.getMapper(BillMapper.class);

	@Mappings({ 
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "paymentInstallments", ignore = true)
		})
	Bill toBillCreateDto(BillCreateDto billCreateDto);

}
