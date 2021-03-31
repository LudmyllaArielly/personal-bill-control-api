package com.ludmylla.personal.bill.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ludmylla.personal.bill.mapper.PaymentInstallmentsMapper;
import com.ludmylla.personal.bill.model.PaymentInstallments;
import com.ludmylla.personal.bill.model.dto.PaymentInstallmentsCreateDto;
import com.ludmylla.personal.bill.service.PaymentInstallmentsService;

@RestController
@RequestMapping("/paymentInstallments")
public class PaymentInstallmentsResource {
	
	@Autowired
	private PaymentInstallmentsService paymentInstallmentsService;

	@PostMapping
	public ResponseEntity<String> createPortion(@RequestBody PaymentInstallmentsCreateDto paymentInstallmentsCreateDto){
		PaymentInstallments payment = PaymentInstallmentsMapper.INSTANCE.toPaymentInstallmentsCreateDto(paymentInstallmentsCreateDto);
		paymentInstallmentsService.save(payment);
		return ResponseEntity.status(HttpStatus.CREATED).body(" PaymentInstallments successfully registered!  ");
	}
}
