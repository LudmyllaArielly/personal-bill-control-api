package com.ludmylla.personal.bill.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ludmylla.personal.bill.model.Bill;
import com.ludmylla.personal.bill.model.Category;
import com.ludmylla.personal.bill.model.Pay;
import com.ludmylla.personal.bill.model.PaymentInstallments;
import com.ludmylla.personal.bill.model.enums.AccountType;
import com.ludmylla.personal.bill.model.enums.ValueType;
import com.ludmylla.personal.bill.repository.BillRepository;
import com.ludmylla.personal.bill.useful.Useful;

@Service
public class BillServiceImpl implements BillService {

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private PaymentInstallmentsService paymentInstallmentsService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private PayService payService;

	@Autowired
	private UserService userService;

	@Override
	public Long save(Bill bill) {
		validationsSave(bill);
		bill.setUsername(userService.takesTheEmailOfTheUserLogin());
		Bill billCreate = billRepository.save(bill);
		return billCreate.getId();
	}

	@Override
	public List<Bill> listAll() {
		validationsTokenIsNullAndValidUserAccess();
		List<Bill> bill = new ArrayList<Bill>();
		List<Bill> list = billRepository.findAll();
		list.forEach(bill::add);
		return list;
	}

	@Override
	public List<Bill> findsAllUserAccounts() {
		validIfTokenIsNull();
		List<Bill> bill = billRepository.findByName(userService.takesTheEmailOfTheUserLogin());
		return bill;
	}
	
	@Override
	public List<Bill> findBillPaymentInstallmentByDate(Date dateStart, Date dateEnd) {
		validIfTokenIsNull();
		List<Bill> bill = billRepository.findBillPaymentInstallmentByDate(dateStart, dateEnd);
		bill = validIfTheReturnedDataBelongsToUser(bill);
		return bill;
	}
	
	@Override
	public List<Bill> findBillByDate(Date dateStart, Date dateEnd) {
		validIfTokenIsNull();
		List<Bill> bill = billRepository.findBillByDate(dateStart, dateEnd);
		bill = validIfTheReturnedDataBelongsToUser(bill);
		return bill;
	}

	@Override
	public List<Bill> findAccountTypeAndValueType(AccountType accountType, ValueType valueType) {
		validIfTokenIsNull();
		List<Bill> bill = billRepository.findByAccountTypeAndValueType(accountType, valueType);
		bill = validIfTheReturnedDataBelongsToUser(bill);
		return bill;
	}
	
	@Override
	public List<Bill> findBillByAttributes(String description, String justification,BigDecimal priceTotal) {
		validIfTokenIsNull();
		List<Bill> bill = billRepository.findBillByAttributes(description, justification, priceTotal);
		bill = validIfTheReturnedDataBelongsToUser(bill);
		return bill;
	}
	
	@Modifying
	@Transactional
	@Override
	public void update(Bill bill) throws ParseException {
		bill.setUsername(userService.takesTheEmailOfTheUserLogin());
		validationsUpdate(bill);
		billRepository.save(bill);
	}

	@Override
	public void delete(Long id) {
		validationsDelete(id);
		Optional<Bill> billOptional = billRepository.findById(id);
		Bill bill = billOptional.get();
		deletesExistingInstallmentsInTheAccount(bill);
		billRepository.delete(bill);
	}
	
	private void validationsSave(Bill bill){
		validIfTokenIsNull();
		validIfAttributesOfBillIsNull(bill);
		validIfCategoryAndPayOfBillIsNull(bill);
		validIfPriceTotalAndQuantityOfBillIsNull(bill);
		validIfAttributesOfBillAreBlank(bill);
		validIfCategoryAndPayOfBillIsBlank(bill);
		validIfBillPriceTotalIsEqualsToZero(bill);
		validIfAttributesOfBillContainsOrSpecialCharacters(bill);
		returnsAccountRelationship(bill);
	}
	
	private void validationsUpdate(Bill bill) throws ParseException {
		validIfTokenIsNull();
		validIfBillExits(bill.getId());
		validIfAttributesOfBillIsNull(bill);
		validIfCategoryAndPayOfBillIsNull(bill);
		validIfPriceTotalAndQuantityOfBillIsNull(bill);
		validIfAttributesOfBillAreBlank(bill);
		validIfBillPriceTotalIsEqualsToZero(bill);
		validIfCategoryAndPayOfBillIsBlank(bill);
		validIfAttributesOfBillContainsOrSpecialCharacters(bill);
		deletesExistingInstallmentsInTheAccount(bill);
		returnsAccountRelationship(bill);
	}
	
	private void validationsDelete(Long id) {
		validIfTokenIsNull();
		validIfBillExits(id);
	}
	
	private void validationsTokenIsNullAndValidUserAccess() {
		validIfTokenIsNull();
		validUserAccess();
	}

	private void returnsAccountRelationship(Bill bill) {
		thereIsInstallment(bill);
		findBillPayment(bill);
		findBillCategory(bill);
	}

	private void thereIsInstallment(Bill bill){
		BigDecimal quantityIsGreaterThanZero = bill.getQuantityPaymentInstallments();
		if (quantityIsGreaterThanZero.compareTo(BigDecimal.ZERO) > 0) {
			List<PaymentInstallments> payment = paymentInstallmentsService.paymentCalculation(bill);
			bill.setPaymentInstallments(payment);
		}
	}

	private void findBillPayment(Bill bill) {
		Pay pay = payService.findByDescription(bill.getPay().getDescription());
		payService.validIfPayIsNull(pay);
		bill.setPay(pay);
	}
	
	private void findBillCategory(Bill bill) {
		Category category = categoryService.findByName(bill.getCategory().getName());
		categoryService.validIfCategoryIsNull(category);
		bill.setCategory(category);
	}
		
	@Transactional
	private void deletesExistingInstallmentsInTheAccount(Bill bill) {
		Optional<Bill> billOptional = billRepository.findById(bill.getId());
		bill = billOptional.get();
		for (int i = 0; i < bill.getPaymentInstallments().size(); i++) {
			paymentInstallmentsService.delete(billOptional.get().getPaymentInstallments().get(i).getId());
		}
	}
	
	private void validIfTokenIsNull() {
		userService.validIfTokenIsNull();
	}

	private void validUserAccess() {
		userService.releasesAuthorizationForTheUser();
	}

	private void validIfBillExits(Long id) {
		Optional<Bill> bill = billRepository.findById(id);
		boolean isBillExist = bill.isEmpty();
		if (isBillExist) {
			throw new IllegalArgumentException("Bill does not exist.");
		}
	}

	private void validIfAttributesOfBillIsNull(Bill bill) {
		Useful.validIfAttributesIsNull(bill.getDescription());
		Useful.validIfAttributesIsNullDate(bill.getPurchaseDate());
		Useful.validIfAttributesIsNullEnums(bill.getAccountType());
		Useful.validIfAttributesIsNullEnums(bill.getValueType());
	}

	private void validIfAttributesOfBillAreBlank(Bill bill) {
		Useful.validIfAttributesAreBlank(bill.getDescription());
		Useful.validIfAttributesAreBlankDate(bill.getPurchaseDate());

	}

	private void validIfBillPriceTotalIsEqualsToZero(Bill bill) {
		boolean isPriceTotalEqualsToZero = bill.getPriceTotal().compareTo(BigDecimal.ZERO) <= 0;
		if (isPriceTotalEqualsToZero) {
			throw new IllegalArgumentException("PriceTotal: cannot be zero!");
		}
	}

	private void validIfCategoryAndPayOfBillIsBlank(Bill bill) {
		boolean isCategoryBlank = bill.getCategory().getName().isBlank();
		boolean isPayBlank = bill.getPay().getDescription().isBlank();
		if (isPayBlank || isCategoryBlank) {
			throw new IllegalArgumentException("There are one or more blank fields.");
		}
	}
		
	private void validIfCategoryAndPayOfBillIsNull(Bill bill) {
		boolean isCategoryNull = bill.getCategory().getName() == null;
		boolean isPayNull = bill.getPay().getDescription() == null;
		if (isPayNull || isCategoryNull) {
			throw new IllegalArgumentException("There are one or more null fields.");
		}
	}
	
	private void validIfPriceTotalAndQuantityOfBillIsNull(Bill bill) {
		boolean isPriceTotalNull = bill.getPriceTotal() == null;
		boolean isQuantityPaymentInstallment = bill.getQuantityPaymentInstallments() == null;
		if(isPriceTotalNull || isQuantityPaymentInstallment) {
			throw new IllegalArgumentException("There are one or more null fields.");
		}
	}
	
	private void validIfAttributesOfBillContainsOrSpecialCharacters(Bill bill) {
		Useful.validIfItHasNumbersOrSpecialCharacters(bill.getDescription());
	}

	private List<Bill> validIfTheReturnedDataBelongsToUser(List<Bill> bill) {	
		List<Bill> list = new ArrayList<Bill>();
		for(int i=0; i<bill.size(); i++) {
			String isUsername = bill.get(i).getUsername();
			String isUsernameLogged = userService.takesTheEmailOfTheUserLogin();
			if(isUsername.equalsIgnoreCase(isUsernameLogged)) {
				list.add(bill.get(i));
			}
		}
		return list;
	}

}
