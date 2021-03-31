package com.ludmylla.personal.bill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ludmylla.personal.bill.model.Bill;

@Repository
@Transactional
public interface BillRepository extends JpaRepository<Bill, Long>{

}
