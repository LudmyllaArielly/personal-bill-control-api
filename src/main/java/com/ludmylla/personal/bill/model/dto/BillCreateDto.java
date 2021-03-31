package com.ludmylla.personal.bill.model.dto;

import java.io.Serializable;
import java.util.Date;

public class BillCreateDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String description;
	private Date purchaseDate;
	private Double priceTotal;
	private Double installmentsQuantity;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Double getPriceTotal() {
		return priceTotal;
	}

	public void setPriceTotal(Double priceTotal) {
		this.priceTotal = priceTotal;
	}

	public Double getInstallmentsQuantity() {
		return installmentsQuantity;
	}

	public void setInstallmentsQuantity(Double installmentsQuantity) {
		this.installmentsQuantity = installmentsQuantity;
	}

}
