package com.ludmylla.personal.bill.service;

import com.ludmylla.personal.bill.model.Token;
import com.ludmylla.personal.bill.model.dto.UserCreateDto;
import com.ludmylla.personal.bill.model.dto.UserLoginDto;
import com.ludmylla.personal.bill.model.dto.UserSearchDto;

public interface UserService {
	
	UserCreateDto create(UserCreateDto userCreateDto);
	
	Token login(UserLoginDto userLoginDto);
	
	UserSearchDto search(UserSearchDto userSearchDto);

	String takesTheRoleOfTheUser();
	 
	String takesTheEmailOfTheUser();
	
	String takesTheEmailOfTheUserLogin();
	
	void validIfTokenIsNull();
	
	void releasesAuthorizationForTheUser();

}
