package com.ludmylla.personal.bill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		receivesEmailAndUserRoles(userAuthorize);
		UserAuthorize userSave = userAuthorizeRepository.save(userAuthorize);
		return userSave.getId();
	}
	
	private void receivesEmailAndUserRoles(UserAuthorize userAuthorize) {
		String user = userService.takesTheEmailOfTheuser();
		String role = userService.takesTheRoleOfTheuser();
		userAuthorize.setEmail(user);
		userAuthorize.setRole(role);
	}
	
	@Transactional
	@Override
	public UserAuthorize findEmailByUser(String email) {
		UserAuthorize userAuthorize = userAuthorizeRepository.findEmailByUser(email);
		return userAuthorize;
	}

}
