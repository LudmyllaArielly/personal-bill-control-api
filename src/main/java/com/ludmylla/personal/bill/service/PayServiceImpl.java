package com.ludmylla.personal.bill.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ludmylla.personal.bill.model.Pay;
import com.ludmylla.personal.bill.repository.PayRespository;
import com.ludmylla.personal.bill.useful.Useful;

@Service
public class PayServiceImpl implements PayService {
	
	@Autowired
	private PayRespository payRespository;
	
	@Transactional
	@Override
	public Long save(Pay pay) {
		validations(pay);
		Pay payCreate = payRespository.save(pay);
		return payCreate.getId();
	}
	
	@Transactional
	@Override
	public List<Pay> listAll() {
		List<Pay> payments = payRespository.findAll();
		return payments;
	}

	@Transactional
	@Override
	public Pay findByDescription(String description) {
		Pay payments = payRespository.findByDescription(description);
		return payments;
	}
	
	@Modifying
	@Transactional
	@Override
	public Long update(Pay pay) {
		validIfPaymentsExist(pay.getId());
		validations(pay);
		Pay payUpdate = payRespository.save(pay);
		return payUpdate.getId();
	}
	
	@Transactional
	@Override
	public void delete(Long id) {
		validIfPaymentsExist(id);
		Optional<Pay> pay = payRespository.findById(id);
		Pay payments = pay.get();
		payRespository.delete(payments);
	}
	
	private void validations(Pay pay) {
		validIfDescriptionIsNull(pay);
		validIfDescriptionIsBlank(pay);
		validTheMinAndMaxOfCharactersInPayments(pay);
		validIfTheDescriptionHasNumbersOrSpecialCharacters(pay);
	}
	
	private void validIfDescriptionIsBlank(Pay pay) {
		Useful.validIfAttributesAreBlank(pay.getDescription());
	}
	
	private void validIfDescriptionIsNull(Pay pay) {
		Useful.validIfAttributesIsNull(pay.getDescription());
	}

	private void validIfPaymentsExist(Long id) {
		Optional<Pay> pay = payRespository.findById(id);
		boolean isPaymentExist = pay.isEmpty();
		if(isPaymentExist) {
			throw new IllegalArgumentException("Payment does not exist.");
		}
	}
	
	private void validTheMinAndMaxOfCharactersInPayments(Pay pay) {
		String isMinAndMaxInPayments = pay.getDescription();
		if(isMinAndMaxInPayments.length() < 3 || isMinAndMaxInPayments.length() > 30) {
			throw new IllegalArgumentException("Payment must be a minimum of 3 characters and a maximum of 30.");
		}
	}
	
	private void validIfTheDescriptionHasNumbersOrSpecialCharacters(Pay pay) {
		String hasNumbersOrSpecialCharactersInDescription = pay.getDescription();
		Useful.validIfItHasNumbersOrSpecialCharacters(hasNumbersOrSpecialCharactersInDescription);
	}
}
