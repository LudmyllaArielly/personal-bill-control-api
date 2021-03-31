package com.ludmylla.personal.bill.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ludmylla.personal.bill.model.Bill;
import com.ludmylla.personal.bill.model.PaymentInstallments;
import com.ludmylla.personal.bill.repository.BillRepository;

@Service
public class BillServiceImpl implements BillService {

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private PaymentInstallmentsService paymentInstallmentsService;
	
	@Override
	public Long save(Bill bill) {
		List<PaymentInstallments> paymentInstallments = paymentInstallmentsService.paymentCalculation(bill);

		bill.setPaymentInstallments(paymentInstallments);
		Bill billCreate = billRepository.save(bill);
		return billCreate.getId();
	}
	
	@Override
	public void getDataBill(Bill bill, Double getInstallmentsquantity, Double getPriceTotal, Date getPurchaseDate) {
		getInstallmentsquantity = bill.getInstallmentsQuantity();
		getPriceTotal = bill.getPriceTotal();
		getPurchaseDate = bill.getPurchaseDate();
	}
	

}
