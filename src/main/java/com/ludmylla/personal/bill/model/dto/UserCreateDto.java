package com.ludmylla.personal.bill.model.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.ludmylla.personal.bill.model.Roles;

public class UserCreateDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String cpf;
	private String email;
	private String name;
	private String lastName;
	private String password;
	private Date dateOfBirth;

	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Roles.class)
	private List<Roles> roles;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<Roles> getRoles() {
		return roles;
	}

	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "UserCreateDto [cpf=" + cpf + ", email=" + email + ", name=" + name + ", lastName=" + lastName
				+ ", password=" + password + ", dateOfBirth=" + dateOfBirth + ", roles=" + roles + "]";
	}
}
