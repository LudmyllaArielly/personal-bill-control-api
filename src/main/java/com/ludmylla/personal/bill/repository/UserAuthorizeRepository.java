package com.ludmylla.personal.bill.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ludmylla.personal.bill.model.UserAuthorize;

@Repository
@Transactional
public interface UserAuthorizeRepository  extends JpaRepository<UserAuthorize, Long>{
	
	@Query("select u from UserAuthorize u where lower(u.email) = lower(?1)")
	UserAuthorize findEmailByUser(String email);
}
