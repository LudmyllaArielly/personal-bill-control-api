package com.ludmylla.personal.bill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ludmylla.personal.bill.model.Solicitation;

@Transactional
@Repository
public interface SolicitationRepository extends JpaRepository<Solicitation, Long>{
	
	@Query("select u from Solicitation u where lower(u.username) = lower(?1) order by u.solicitationDate ASC")
	List<Solicitation> findsAllUserSolicitation(String username);
	
	
	@Query("select u from Solicitation u order by u.solicitationDate ASC")
	List<Solicitation> findAllSolicitationOfUser();
}
