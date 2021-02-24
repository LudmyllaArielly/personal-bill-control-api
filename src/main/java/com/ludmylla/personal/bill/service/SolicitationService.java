package com.ludmylla.personal.bill.service;

import java.util.List;

import com.ludmylla.personal.bill.model.Solicitation;

public interface SolicitationService {
	
	Long save(Solicitation solicitation);
	
	List<Solicitation> listAll();
	
	List<Solicitation> findBySolicitationsOfUser();
	
	void update(Solicitation solicitation);
	
	void delete(Long id);

}
