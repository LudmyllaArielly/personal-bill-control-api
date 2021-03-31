package com.ludmylla.personal.bill.service;

import java.util.Date;

import com.ludmylla.personal.bill.model.Bill;

public interface BillService {
	
	Long save(Bill bill);
	
	void getDataBill(Bill bill, Double getInstallmentsquantity, Double getPriceTotal, Date getPurchaseDate); 

}
