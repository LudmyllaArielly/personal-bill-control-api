package com.ludmylla.personal.bill.model.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.ludmylla.personal.bill.model.enums.Status;

public class SolicitationListAllDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String description;
	private ZonedDateTime solicitationDate;

	@Enumerated(EnumType.STRING)
	private Status status;

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

	public ZonedDateTime getSolicitationDate() {
		return solicitationDate;
	}

	public void setSolicitationDate(ZonedDateTime solicitationDate) {
		this.solicitationDate = solicitationDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
