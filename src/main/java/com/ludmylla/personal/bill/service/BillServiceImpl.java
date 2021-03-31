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
		validations(bill);
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
	public List<Bill> findUserBill() {
		validIfTokenIsNull();
		List<Bill> bill = billRepository.findByName(userService.takesTheEmailOfTheUserLogin());
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
		validations(id);
		Optional<Bill> billOptional = billRepository.findById(id);
		Bill bill = billOptional.get();
		deletesExistingInstallmentsInTheAccount(bill);
		billRepository.delete(bill);
	}

	private void returnsAccountRelationship(Bill bill) {
		thereIsInstallment(bill);
		findBillPayment(bill);
		findBillCategory(bill);
	}

	/*
	 * Verifica se a quantidade de parcela é maior que zero, se sim existe
	 * parcelamento se não é porque é a vista
	 */
	private void thereIsInstallment(Bill bill){
		BigDecimal quantityIsGreaterThanZero = bill.getQuantityPaymentInstallments();
		if (quantityIsGreaterThanZero.compareTo(BigDecimal.ZERO) > 0) {
			List<PaymentInstallments> payment = paymentInstallmentsService.paymentCalculation(bill);
			bill.setPaymentInstallments(payment);
		}
	}

	private void findBillPayment(Bill bill) {
		Pay pay = payService.findByDescription(bill.getPay().getDescription());
		bill.setPay(pay);
	}

	private void findBillCategory(Bill bill) {
		Category category = categoryService.findByName(bill.getCategory().getName());
		bill.setCategory(category);
	}
	/* Método chamado pelo update para deletar as parcelas existente, deixando apenas as nova
	 * parcelas geradas. Chamado também pelo delete após excluir a conta, exclui também 
	 * as parcelas.
	 * */
	@Transactional
	private void deletesExistingInstallmentsInTheAccount(Bill bill) {
		Optional<Bill> billOptional = billRepository.findById(bill.getId());
		bill = billOptional.get();
		for (int i = 0; i < bill.getPaymentInstallments().size(); i++) {
			paymentInstallmentsService.delete(billOptional.get().getPaymentInstallments().get(i).getId());
		}
	}

	private void validationsUpdate(Bill bill) throws ParseException {
		validIfTokenIsNull();
		validIfBillExits(bill.getId());
		validIfBillPriceTotalIsEqualsToZero(bill);
		validIfAttributesOfBillIsNull(bill);
		validIfAttributesOfBillAreBlank(bill);
		validIfAttributesOfBillIsBlank(bill);
		validIfAttributesOfBillContainsOrSpecialCharacters(bill);
		deletesExistingInstallmentsInTheAccount(bill);
		returnsAccountRelationship(bill);
	}

	private void validations(Bill bill){
		validIfTokenIsNull();
		validIfAttributesOfBillIsNull(bill);
		validIfAttributesOfBillAreBlank(bill);
		validIfAttributesOfBillContainsOrSpecialCharacters(bill);
		returnsAccountRelationship(bill);
	}

	private void validationsTokenIsNullAndValidUserAccess() {
		validIfTokenIsNull();
		validUserAccess();
	}

	private void validations(Long id) {
		validIfTokenIsNull();
		validIfBillExits(id);
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
		Useful.validIfAttributesIsNullObject(bill.getCategory());
		Useful.validIfAttributesIsNullObject(bill.getPay());
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

	private void validIfAttributesOfBillIsBlank(Bill bill) {
		boolean isCategoryBlank = bill.getCategory().getName().isBlank();
		boolean isPayBlank = bill.getPay().getDescription().isBlank();
		boolean isAccountTypeBlank = bill.getAccountType().toString().isBlank();
		boolean isValueTypeBlank = bill.getValueType().toString().isBlank();
		if (isPayBlank || isCategoryBlank || isAccountTypeBlank || isValueTypeBlank) {
			throw new IllegalArgumentException("There are one or more nullfilds");
		}
	}
	
	private void validIfAttributesOfBillContainsOrSpecialCharacters(Bill bill) {
		Useful.validIfItHasNumbersOrSpecialCharacters(bill.getDescription());
	}
	/*
	 Verifica se a lista contém o parametro especificado e retorna e retorna
	 os dados somente do usuário logado
	 */
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
	public List<Bill> findBillAttributes(String description, String justification,BigDecimal priceTotal) {
		validIfTokenIsNull();
		List<Bill> bill = billRepository.findBillAttributes(description, justification, priceTotal);
		bill = validIfTheReturnedDataBelongsToUser(bill);
		return bill;
	}
	
}