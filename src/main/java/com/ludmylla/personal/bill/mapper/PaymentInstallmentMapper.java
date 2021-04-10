package com.ludmylla.personal.bill.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ludmylla.personal.bill.model.PaymentInstallments;
import com.ludmylla.personal.bill.model.dto.PaymentInstallmentCreateDto;

@Mapper(uses = { BillMapper.class })
public interface PaymentInstallmentMapper {

	PaymentInstallmentMapper INSTANCE = Mappers.getMapper(PaymentInstallmentMapper.class);

	@Mapping(target = "id", ignore = true)
	PaymentInstallments toPaymentInstallment(PaymentInstallmentCreateDto source);

	PaymentInstallmentCreateDto dtoPaymentInstallmentCreateDto(PaymentInstallments source);

	List<PaymentInstallmentCreateDto> dtoPaymentInstallmentCreateDto(List<PaymentInstallments> source);

}
