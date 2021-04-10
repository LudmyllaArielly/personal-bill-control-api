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
			@Mapping(target = "category", source = "categoryCreateAndListAllDto"),
			@Mapping(target = "pay", source = "payCreateAndListAllDto") })
	Bill toBill(BillCreateDto source);

	@Mappings({ @Mapping(target = "paymentInstallments", ignore = true), 
			@Mapping(target = "username", ignore = true),
			@Mapping(target = "category", source = "categoryCreateAndListAllDto"),
			@Mapping(target = "pay", source = "payCreateAndListAllDto") })
	Bill toBill(BillUpdateDto source);
	
	@Mappings({
		@Mapping(target = "paymentInstallmentCreateDtos", source = "paymentInstallments"),
		@Mapping(target = "categoryCreateAndListAllDto", source = "category"),
		@Mapping(target = "payCreateAndListAllDto", source = "pay")
	})
	BillListAllDto dtoBillListAllDto(Bill source);

	@Mappings({ 
			@Mapping(target = "paymentInstallments", source = "paymentInstallmentCreateDtos"),
			@Mapping(target = "category", source = "categoryCreateAndListAllDto"),
			@Mapping(target = "pay", source = "payInsertAndListAllDto") })
	List<BillListAllDto> dtoBillListAllDto(List<Bill> source);

}
