package com.ludmylla.personal.bill.service;

import java.util.List;

import com.ludmylla.personal.bill.model.Bill;
import com.ludmylla.personal.bill.model.PaymentInstallments;

public interface PaymentInstallmentsService {
	
	Long save(PaymentInstallments paymentInstallments);
	
	 void takesBillDataForPaymentCalculation(Bill bill);
	 
	 List<PaymentInstallments> paymentCalculation(Bill bill);
	 
}
