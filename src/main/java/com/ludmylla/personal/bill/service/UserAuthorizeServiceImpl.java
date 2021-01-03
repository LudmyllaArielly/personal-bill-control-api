package com.ludmylla.personal.bill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ludmylla.personal.bill.model.UserAuthorize;
import com.ludmylla.personal.bill.repository.UserAuthorizeRepository;

@Service
public class UserAuthorizeServiceImpl implements UserAuthorizeService {
	
	@Autowired
	private UserAuthorizeRepository userAuthorizeRepository;
	
	@Autowired
	private UserService userService;

	@Override
	public Long save() {
		UserAuthorize userAuthorize = new UserAuthorize();
		String user = userService.takesTheEmailOfTheuser();
		userAuthorize.setEmail(user);
		String role = userService.takesTheRoleOfTheuser();
		userAuthorize.setRole(role);
		UserAuthorize userSave = userAuthorizeRepository.save(userAuthorize);
		return userSave.getId();
	}

	@Override
	public UserAuthorize findEmailByUser(String email) {
		UserAuthorize userAuthorize = userAuthorizeRepository.findEmailByUser(email);
		return userAuthorize;
	}

}
