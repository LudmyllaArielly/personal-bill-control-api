package com.ludmylla.personal.bill.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.ludmylla.personal.bill.model.Bill;
import com.ludmylla.personal.bill.model.dto.BillCreateDto;
import com.ludmylla.personal.bill.model.dto.BillListAllDto;
import com.ludmylla.personal.bill.model.dto.BillUpdateDto;

@Mapper(uses = { CategoryMapper.class, PayMapper.class, PaymentInstallmentMapper.class })
public interface BillMapper {

	BillMapper INSTANCE = Mappers.getMapper(BillMapper.class);

	@Mappings({ 
			@Mapping(target = "id", ignore = true),
			@Mapping(target = "username", ignore = true),
			@Mapping(target = "paymentInstallments", ignore = true),
			@Mapping(target = "category", source = "categoryInsertDtos"),
			@Mapping(target = "pay", source = "payInsertAndListAllDto") })
	Bill toBillCreateDto(BillCreateDto source);

	@Mappings({
			@Mapping(target = "paymentInstallments", ignore = true),
			@Mapping(target = "username", ignore = true),
			@Mapping(target = "category", source = "categoryInsertDtos"),
			@Mapping(target = "pay", source = "payInsertAndListAllDto") })
	Bill toBillUpdateDto(BillUpdateDto source);

	@Mapping(target = "paymentInstallmentCreateDtos", source = "paymentInstallments")
	@Mapping(target = "categoryInsertDtos", source = "category")
	@Mapping(target = "payInsertAndListAllDto", source = "pay")
	BillListAllDto dtoBillListAllDto(Bill source);

	@Mappings({ 
			@Mapping(target = "paymentInstallments", source = "paymentInstallmentCreateDtos"),
			@Mapping(target = "category", source = "categoryInsertDtos"),
			@Mapping(target = "pay", source = "payInsertAndListAllDto") })
	List<BillListAllDto> dtoBillListAllDto(List<Bill> source);

}
