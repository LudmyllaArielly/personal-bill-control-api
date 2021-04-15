package com.ludmylla.personal.bill.service;

import java.util.List;

import com.ludmylla.personal.bill.model.Bill;
import com.ludmylla.personal.bill.model.PaymentInstallments;

public interface PaymentInstallmentsService {

	List<PaymentInstallments> paymentCalculation(Bill bill);

	void delete(Long id);

}
