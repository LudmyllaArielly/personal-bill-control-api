package com.ludmylla.personal.bill.service;

import static java.util.Calendar.MONTH;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ludmylla.personal.bill.model.Bill;
import com.ludmylla.personal.bill.model.PaymentInstallments;
import com.ludmylla.personal.bill.repository.PaymentInstallmentsRepository;
import com.ludmylla.personal.bill.useful.Useful;

@Service
public class PaymentInstallmentsServiceImpl implements PaymentInstallmentsService {

	@Autowired
	private PaymentInstallmentsRepository paymentInstallmentsRepository;
	
	@Autowired
	private BillService billService;

	@Override
	public Long save(PaymentInstallments paymentInstallments) {
		PaymentInstallments paymentCreate = paymentInstallmentsRepository.save(paymentInstallments);
		return paymentCreate.getId();
	}
	
	private List<PaymentInstallments> takesBillDataForPaymentCalculation(List<PaymentInstallments> paymentInstallments){
		
		
		
		return paymentInstallments;
	}
	
	@Override
	@Transactional
	public void takesBillDataForPaymentCalculation(Bill bill) {
		Double getInstallmentsquantity = bill.getInstallmentsQuantity();
		Double getPriceTotal = bill.getPriceTotal();
		Date getPurchaseDate = bill.getPurchaseDate();
	
		//paymentCalculation(getInstallmentsquantity, getPurchaseDate, getPriceTotal);
	
	}
	
	@Override
	public List<PaymentInstallments> paymentCalculation(Bill bill) {
		
		GregorianCalendar calendar = new GregorianCalendar();
		List<PaymentInstallments> list = new ArrayList<>();
		List<PaymentInstallments> paymentInstallments = new ArrayList<>();
		
		Double getInstallmentsquantity = bill.getInstallmentsQuantity();
		Double getPriceTotal = bill.getPriceTotal();
		Date getPurchaseDate = bill.getPurchaseDate();
		//billService.getDataBill(null, getInstallmentsquantity, getPriceTotal, getPurchaseDate);
		
		for (int i = 0; i < getInstallmentsquantity; i++) {
			calculateInstallment(getInstallmentsquantity, getPriceTotal);
		
			calendar.setTime(getPurchaseDate);
			addsMonth(calendar, i);
			getPurchaseDate = calendar.getTime();
			 
			
			list.add(paymentInstallments.get(i));
			
		
			//paymentInstallments.setInstallmentDate(Useful.formattDate(getPurchaseDate));
			//paymentInstallments.set(i, Useful.formattDate(getPurchaseDate));
			//paymentInstallments.setInstallmentPrice(Useful.roundsValue(calculateInstallment(getInstallmentsquantity, getPriceTotal)));
			
			Useful.roundsValue(calculateInstallment(getInstallmentsquantity, getPriceTotal));
			Useful.formattDate(getPurchaseDate);
			System.out.println("Data: " + Useful.formattDate(getPurchaseDate));
			System.out.println("Parcela: " + Useful.roundsValue(calculateInstallment(getInstallmentsquantity, getPriceTotal)));
			
			savePaymentInstallments(paymentInstallments);
		}
		return paymentInstallments;
	}
	
	private void savePaymentInstallments(List<PaymentInstallments> paymentInstallments) {
		paymentInstallmentsRepository.saveAll(paymentInstallments);
	}
	
	private Double calculateInstallment(Double getInstallmentsquantity,Double getPriceTotal) {
		Double installmentPurchase = (getPriceTotal / getInstallmentsquantity);
		return installmentPurchase;
	}
	
	private void addsMonth(GregorianCalendar calendar, int i) {
		if (i == 0) {
			calendar.add(MONTH, 0);
		} else {
			calendar.add(MONTH, 1);
		}
	}
}
