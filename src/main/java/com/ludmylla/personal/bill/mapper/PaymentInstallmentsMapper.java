package com.ludmylla.personal.bill.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.ludmylla.personal.bill.model.PaymentInstallments;
import com.ludmylla.personal.bill.model.dto.PaymentInstallmentsCreateDto;

@Mapper
public interface PaymentInstallmentsMapper {
	
	PaymentInstallmentsMapper INSTANCE = Mappers.getMapper(PaymentInstallmentsMapper.class);
	
	@Mappings({
		@Mapping(target = "id", ignore = true)})
	PaymentInstallments toPaymentInstallmentsCreateDto(PaymentInstallmentsCreateDto paymentInstallmentsCreateDto); 

}
