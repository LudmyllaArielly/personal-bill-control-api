package com.ludmylla.personal.bill.model.dto;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.ludmylla.personal.bill.model.enums.Status;

public class SolicitationUpdateStatusDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@Enumerated(EnumType.STRING)
	private Status status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
