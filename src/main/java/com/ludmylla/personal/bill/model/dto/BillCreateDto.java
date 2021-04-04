package com.ludmylla.personal.bill.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.OneToOne;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ludmylla.personal.bill.model.enums.AccountType;
import com.ludmylla.personal.bill.model.enums.ValueType;
import java.util.Date;

public class BillCreateDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String description;
	@JsonDeserialize(as = Date.class)
	private Date purchaseDate;
	private BigDecimal priceTotal;
	private BigDecimal quantityPaymentInstallments;
	private String justification;

	private AccountType accountType;

	private ValueType valueType;

	@OneToOne
	private CategoryInsertDto categoryInsertDtos;

	@OneToOne
	private PayInsertAndListAllDto payInsertAndListAllDto;

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

	public CategoryInsertDto getCategoryInsertDtos() {
		return categoryInsertDtos;
	}

	public void setCategoryInsertDtos(CategoryInsertDto categoryInsertDtos) {
		this.categoryInsertDtos = categoryInsertDtos;
	}

	public PayInsertAndListAllDto getPayInsertAndListAllDto() {
		return payInsertAndListAllDto;
	}

	public void setPayInsertAndListAllDto(PayInsertAndListAllDto payInsertAndListAllDto) {
		this.payInsertAndListAllDto = payInsertAndListAllDto;
	
	}

}
