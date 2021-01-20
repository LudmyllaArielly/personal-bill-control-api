package com.ludmylla.personal.bill.model.dto;

import java.io.Serializable;

public class UserSearchDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;
	private String cpf;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public String toString() {
		return "UserSearchDto [email=" + email + ", cpf=" + cpf + "]";
	}

}
