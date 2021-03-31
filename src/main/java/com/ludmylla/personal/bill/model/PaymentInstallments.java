package com.ludmylla.personal.bill.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PaymentInstallments implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Double installmentPrice;
	private Integer installmentNumber;
	private String installmentDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
		PaymentInstallments other = (PaymentInstallments) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PaymentInstallments [id=" + id + ", installmentPrice=" + installmentPrice + ", installmentNumber="
				+ installmentNumber + ", installmentDate=" + installmentDate + "]";
	}

}
