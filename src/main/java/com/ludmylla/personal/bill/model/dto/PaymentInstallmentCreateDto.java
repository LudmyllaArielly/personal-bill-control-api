package com.ludmylla.personal.bill.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PaymentInstallmentCreateDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal installmentPrice;
	private Integer installmentNumber;
	private Date installmentDate;

	public BigDecimal getInstallmentPrice() {
		return installmentPrice;
	}

	public void setInstallmentPrice(BigDecimal installmentPrice) {
		this.installmentPrice = installmentPrice;
	}

	public Integer getInstallmentNumber() {
		return installmentNumber;
	}

	public void setInstallmentNumber(Integer installmentNumber) {
		this.installmentNumber = installmentNumber;
	}

	public Date getInstallmentDate() {
		return installmentDate;
	}

	public void setInstallmentDate(Date installmentDate) {
		this.installmentDate = installmentDate;
	}

}
