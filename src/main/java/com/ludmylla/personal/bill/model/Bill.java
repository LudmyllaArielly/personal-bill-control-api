package com.ludmylla.personal.bill.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ludmylla.personal.bill.model.enums.AccountType;
import com.ludmylla.personal.bill.model.enums.ValueType;

@Entity
public class Bill implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String description;
	private BigDecimal priceTotal;
	private BigDecimal quantityPaymentInstallments;

	private String justification;

	@DateTimeFormat(iso = ISO.DATE)
	@JsonDeserialize(as = Date.class)
	@Temporal(TemporalType.DATE)
	private Date purchaseDate;

	@Enumerated(EnumType.STRING)
	private AccountType accountType;

	@Enumerated(EnumType.STRING)
	private ValueType valueType;

	@OneToMany(fetch = FetchType.LAZY)
	private List<PaymentInstallments> paymentInstallments = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "PAY_ID")
	private Pay pay;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public List<PaymentInstallments> getPaymentInstallments() {
		return paymentInstallments;
	}

	public void setPaymentInstallments(List<PaymentInstallments> paymentInstallments) {
		this.paymentInstallments = paymentInstallments;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Pay getPay() {
		return pay;
	}

	public void setPay(Pay pay) {
		this.pay = pay;
	}

	public BigDecimal returnInstallmentPrice() {
		return priceTotal.divide(quantityPaymentInstallments, 2, RoundingMode.HALF_UP);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bill other = (Bill) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {

		return "Bill [id=" + id + ", username=" + username + ", description=" + description + ", priceTotal="
				+ priceTotal + ", quantityPaymentInstallments=" + quantityPaymentInstallments + ", justification="
				+ justification + ", purchaseDate=" + purchaseDate + ", accountType=" + accountType + ", valueType="
				+ valueType + ", paymentInstallments=" + paymentInstallments + ", category=" + category + ", pay=" + pay
				+ "]";
	}

}