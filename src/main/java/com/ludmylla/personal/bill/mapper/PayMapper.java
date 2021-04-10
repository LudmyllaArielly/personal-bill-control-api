package com.ludmylla.personal.bill.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ludmylla.personal.bill.model.Pay;
import com.ludmylla.personal.bill.model.dto.PayCreateAndListAllDto;
import com.ludmylla.personal.bill.model.dto.PayUpdateDto;

@Mapper(uses = { BillMapper.class })
public interface PayMapper {

	PayMapper INSTANCE = Mappers.getMapper(PayMapper.class);

	@Mapping(target = "id", ignore = true)
	Pay toPay(PayCreateAndListAllDto source);

	Pay toPay(PayUpdateDto source);

	PayCreateAndListAllDto dtoPayCreateAndListAllDto(Pay source);

	List<PayCreateAndListAllDto> dtoPayCreateAndListAllDto(List<Pay> source);

}
