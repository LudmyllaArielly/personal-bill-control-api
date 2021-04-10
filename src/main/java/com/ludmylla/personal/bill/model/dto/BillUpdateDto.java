package com.ludmylla.personal.bill.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ludmylla.personal.bill.model.enums.AccountType;
import com.ludmylla.personal.bill.model.enums.ValueType;

public class BillUpdateDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String description;
	private BigDecimal priceTotal;
	private BigDecimal quantityPaymentInstallments;
	private String justification;

	@JsonDeserialize(as = Date.class)
	private Date purchaseDate;

	private AccountType accountType;

	private ValueType valueType;

	private CategoryCreateAndListAllDto categoryCreateAndListAllDto;

	private PayCreateAndListAllDto payCreateAndListAllDto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPriceTotal() {
		return priceTotal;
	}

	public void setPriceTotal(BigDecimal priceTotal) {
		this.priceTotal = priceTotal;
	}

	public BigDecimal getQuantityPaymentInstallments() {
		return quantityPaymentInstallments;
	}

	public void setQuantityPaymentInstallments(BigDecimal quantityPaymentInstallments) {
		this.quantityPaymentInstallments = quantityPaymentInstallments;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public ValueType getValueType() {
		return valueType;
	}

	public void setValueType(ValueType valueType) {
		this.valueType = valueType;
	}

	public CategoryCreateAndListAllDto getCategoryCreateAndListAllDto() {
		return categoryCreateAndListAllDto;
	}

	public void setCategoryCreateAndListAllDto(CategoryCreateAndListAllDto categoryCreateAndListAllDto) {
		this.categoryCreateAndListAllDto = categoryCreateAndListAllDto;
	}

	public PayCreateAndListAllDto getPayCreateAndListAllDto() {
		return payCreateAndListAllDto;
	}

	public void setPayCreateAndListAllDto(PayCreateAndListAllDto payCreateAndListAllDto) {
		this.payCreateAndListAllDto = payCreateAndListAllDto;
	}

}
