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
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BillService billService;
	
	@Transactional
	@Override
	public Long save(Pay pay) {
		validationsSave(pay);
		Pay payCreate = payRespository.save(pay);
		return payCreate.getId();
	}
	
	@Transactional
	@Override
	public List<Pay> listAll() {
		validIfTokenIsNull();
		List<Pay> payments = payRespository.findAll();
		return payments;
	}

	@Transactional
	@Override
	public Pay findByDescription(String description) {
		validIfTokenIsNull();
		Pay payments = payRespository.findByDescription(description);
		return payments;
	}
	
	@Override
	public void validIfPayIsNull(Pay pay) {
		boolean isPayExits = pay == null;
		if(isPayExits) {
			throw new IllegalArgumentException("Pay does not exist!");
		}
	}

	@Modifying
	@Transactional
	@Override
	public Long update(Pay pay) {
		validationsUpdate(pay);
		Pay payUpdate = payRespository.save(pay);
		return payUpdate.getId();
	}
	
	@Transactional
	@Override
	public void delete(Long id) {
		validationsDelete(id);
		Optional<Pay> pay = payRespository.findById(id);
		Pay payments = pay.get();
		payRespository.delete(payments);
	}
	
	private void validationsSave(Pay pay) {
		validIfTokenIsNullAndValidUserAccess();
		validIfDescriptionIsNull(pay);
		validIfDescriptionIsBlank(pay);
		validTheMinAndMaxOfCharactersInPayments(pay);
		validIfTheDescriptionHasNumbersOrSpecialCharacters(pay);
	}
	
	private void validationsUpdate(Pay pay){
		validationsSave(pay);
		validIfPaymentsExist(pay.getId());
	}
	
	private void validationsDelete(Long id) {
		validIfTokenIsNullAndValidUserAccess();
		validIfPaymentsExist(id);
		checkIfThePayIsPartOfTheBill(id);
	}
	
	private void validIfTokenIsNullAndValidUserAccess() {
		validIfTokenIsNull();
		validUserAccess();
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
	
	public void checkIfThePayIsPartOfTheBill(Long id) {
		boolean isPayExistInTheBill = billService.checksWhetherTheCategoryIsInTheBill(id).isEmpty();
		
		if(!isPayExistInTheBill) {
			throw new IllegalArgumentException("The Pay cannot be excluded because it is being used by the bill.");
		}
	}
	
	private void validIfTokenIsNull() {
		userService.validIfTokenIsNull();
	}
	
	private void validUserAccess() {
		userService.releasesAuthorizationForTheUser();
	}

}
