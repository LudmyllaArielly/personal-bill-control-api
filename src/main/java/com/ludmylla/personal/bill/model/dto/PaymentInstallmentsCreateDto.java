package com.ludmylla.personal.bill.model.dto;

import java.io.Serializable;

public class PaymentInstallmentsCreateDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Double installmentPrice;
	private Integer installmentNumber;
	private String installmentDate;

	public Double getInstallmentPrice() {
		return installmentPrice;
	}

	public void setInstallmentPrice(Double installmentPrice) {
		this.installmentPrice = installmentPrice;
	}

	public Integer getInstallmentNumber() {
		return installmentNumber;
	}

	public void setInstallmentNumber(Integer installmentNumber) {
		this.installmentNumber = installmentNumber;
	}

	public String getInstallmentDate() {
		return installmentDate;
	}

	public void setInstallmentDate(String installmentDate) {
		this.installmentDate = installmentDate;
	}

}
