package com.ludmylla.personal.bill.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
public class Bill implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;

	@JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
	@DateTimeFormat(iso = ISO.DATE)
	@JsonDeserialize(as = Date.class)
	@Temporal(TemporalType.DATE)
	// @JsonSerialize(using = LocalDateSerializer.class)
	private Date purchaseDate;
	private Double priceTotal;
	private Double installmentsQuantity;

	@OneToMany( fetch = FetchType.LAZY)
	List<PaymentInstallments> paymentInstallments = new ArrayList<>();

	public Bill() {
	}

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



	public List<PaymentInstallments> getPaymentInstallments() {
		return paymentInstallments;
	}

	public void setPaymentInstallments(List<PaymentInstallments> paymentInstallments) {
		this.paymentInstallments = paymentInstallments;
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
		return "Bill [id=" + id + ", description=" + description + ", purchaseDate=" + purchaseDate + ", priceTotal="
				+ priceTotal + ", installmentsQuantity=" + installmentsQuantity + ", paymentInstallments="
				+ paymentInstallments + "]";
	}

}