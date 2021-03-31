package com.ludmylla.personal.bill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ludmylla.personal.bill.model.PaymentInstallments;

@Repository
@Transactional
public interface PaymentInstallmentsRepository extends JpaRepository<PaymentInstallments, Long>{

}
