package com.ludmylla.personal.bill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ludmylla.personal.bill.model.Pay;

@Repository
@Transactional
public interface PayRespository extends JpaRepository<Pay, Long>{
	
	@Query("select u from Pay u where lower(u.description) = lower(?1)")
	Pay findByDescription(String description);
}
