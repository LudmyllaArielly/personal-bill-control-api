package com.ludmylla.personal.bill.useful;

public class Useful {

	public static String validIfItHasNumbersOrSpecialCharacters(String compare) {
		if (!compare.matches("[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]+$")) {
			throw new IllegalArgumentException("Cannot contain numbers or special characters!");
		}
		return compare;
	}
	
	public static void validIfAttributesAreBlank(String compare) {
		boolean isAttributesBlank = compare.isBlank();
		if(isAttributesBlank) {
			throw new IllegalArgumentException("There are one or more blank fields.");
		}
	}
	
	public static void validIfAttributesIsNull(String compare) {
		boolean isAttributesNull = compare == null;
		if(isAttributesNull) {
			throw new IllegalArgumentException("There are one or more null fields.");
		}
	}	
}
