package com.ludmylla.personal.bill.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ludmylla.personal.bill.model.Bill;
import com.ludmylla.personal.bill.model.enums.AccountType;
import com.ludmylla.personal.bill.model.enums.ValueType;

@Repository
@Transactional
public interface BillRepository extends JpaRepository<Bill, Long> {

	@Query("select u from Bill u where lower(u.username) = lower(?1)")
	List<Bill> findByName(String username);

	@Query("select u from Bill u where lower(u.description) like concat('%',lower(:description),'%') "
			+ "or lower(u.justification) like concat('%',lower(:justification),'%') "
			+ "or lower(u.priceTotal) like concat('%',lower(:priceTotal),'%')")
	List<Bill> findBillAttributes(@Param("description") String description, 
								@Param("justification") String justification,
								@Param("priceTotal") BigDecimal priceTotal);
	
	@Query("select u from Bill u where u.accountType = :accountType "
			+ "or u.valueType = :valueType")
	List<Bill> findByAccountTypeAndValueType(@Param("accountType") AccountType accountType,
											@Param("valueType") ValueType valueType);

	
	@Query("select b from Bill b JOIN b.paymentInstallments p where p.installmentDate between :dataStart and :dateEnd")
	List<Bill> findBillPaymentInstallmentByDate(@Param("dataStart") Date dateStart, @Param("dateEnd") Date dateEnd);
	
	@Query("select u from Bill u where u.purchaseDate between :dataStart and :dateEnd")
	List<Bill> findBillByDate(@Param("dataStart") Date dateStart, @Param("dateEnd") Date dateEnd);
		
}
