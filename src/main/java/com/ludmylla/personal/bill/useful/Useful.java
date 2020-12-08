package com.ludmylla.personal.bill.useful;

public class Useful {

	public static String validIfItHasNumbersOrSpecialCharacters(String compare) {
		if (!compare.matches("[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]+$")) {
			throw new IllegalArgumentException("Cannot contain numbers or special characters!");
		}
		return compare;
	}
}
