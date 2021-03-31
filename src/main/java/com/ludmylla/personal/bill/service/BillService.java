package com.ludmylla.personal.bill.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.ludmylla.personal.bill.model.Bill;
import com.ludmylla.personal.bill.model.enums.AccountType;
import com.ludmylla.personal.bill.model.enums.ValueType;

public interface BillService {

	Long save(Bill bill) throws ParseException;

	void update(Bill bill) throws ParseException;

	List<Bill> listAll();

	void delete(Long id);

	List<Bill> findUserBill();

	List<Bill> findBillAttributes(String description, String justification, BigDecimal priceTotal);

	List<Bill> findBillPaymentInstallmentByDate(Date dateStart, Date dateEnd);
	
	List<Bill> findBillByDate(Date dateStart, Date dateEnd);

	List<Bill> findAccountTypeAndValueType(AccountType accountType, ValueType valueType);

}
