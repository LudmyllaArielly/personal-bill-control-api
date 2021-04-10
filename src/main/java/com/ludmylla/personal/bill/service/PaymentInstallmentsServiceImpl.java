package com.ludmylla.personal.bill.service;

import static java.util.Calendar.MONTH;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ludmylla.personal.bill.model.Bill;
import com.ludmylla.personal.bill.model.PaymentInstallments;
import com.ludmylla.personal.bill.repository.PaymentInstallmentsRepository;

@Service
public class PaymentInstallmentsServiceImpl implements PaymentInstallmentsService {

	@Autowired
	private PaymentInstallmentsRepository paymentInstallmentsRepository;

	@Override
	public List<PaymentInstallments> paymentCalculation(Bill bill) {
		GregorianCalendar calendar = new GregorianCalendar();
		List<PaymentInstallments> paymentInstallments = new ArrayList<PaymentInstallments>();
		BigDecimal getquantity = bill.getQuantityPaymentInstallments();
		Date getPurchaseDate = bill.getPurchaseDate();
		BigDecimal getInstallmentPrice = bill.returnInstallmentPrice();
		for(int i=1; i<=getquantity.doubleValue(); i++) {
		
			calendar.setTime(getPurchaseDate);

			addsMonth(calendar, i);

			getPurchaseDate = calendar.getTime();
			
			PaymentInstallments	payment = new PaymentInstallments(getInstallmentPrice, i,getPurchaseDate);
			paymentInstallments.add(payment);
			
		}
		paymentInstallmentsRepository.saveAll(paymentInstallments);
		return paymentInstallments; 
	}

	private void addsMonth(GregorianCalendar calendar, int i) {
			calendar.add(MONTH, 1);
	}
	
	@Transactional
	@Override
	public void delete(Long id) {
		Optional<PaymentInstallments> paymentsInstallments = paymentInstallmentsRepository.findById(id);
		PaymentInstallments payment = paymentsInstallments.get();
		paymentInstallmentsRepository.delete(payment);
	}
	
}
