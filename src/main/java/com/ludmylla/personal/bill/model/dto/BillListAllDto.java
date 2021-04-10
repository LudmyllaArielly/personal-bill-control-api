package com.ludmylla.personal.bill.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ludmylla.personal.bill.model.enums.AccountType;
import com.ludmylla.personal.bill.model.enums.ValueType;

public class BillListAllDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String description;

	@JsonDeserialize(as = Date.class)
	private Date purchaseDate;
	private BigDecimal priceTotal;
	private BigDecimal quantityPaymentInstallments;
	private String justification;

	private AccountType accountType;

	private ValueType valueType;

	private CategoryCreateAndListAllDto categoryCreateAndListAllDto;

	private PayCreateAndListAllDto payCreateAndListAllDto;

	private List<PaymentInstallmentCreateDto> paymentInstallmentCreateDtos;

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

	public List<PaymentInstallmentCreateDto> getPaymentInstallmentCreateDtos() {
		return paymentInstallmentCreateDtos;
	}

	public void setPaymentInstallmentCreateDtos(List<PaymentInstallmentCreateDto> paymentInstallmentCreateDtos) {
		this.paymentInstallmentCreateDtos = paymentInstallmentCreateDtos;
	}

}
