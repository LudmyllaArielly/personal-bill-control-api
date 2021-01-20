package com.ludmylla.personal.bill.service;

import com.ludmylla.personal.bill.model.UserAuthorize;

public interface UserAuthorizeService {
	
	Long save();
	
	UserAuthorize findEmailByUser(String email);
	
}
