package com.ludmylla.personal.bill.model.dto;

import java.io.Serializable;

public class CategoryCreateAndListAllDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
