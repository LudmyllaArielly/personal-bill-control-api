package com.ludmylla.personal.bill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ludmylla.personal.bill.model.Category;

@Transactional
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	Category findByName(String name);
	
	@Query(value = "select  c.*, CONCAT(b.category_id, \"(\",count(b.id),\")\") as category_id "
			+ "from Bill as b left join category as c on b.category_id=c.id group by category_id "
			+ "order by category_id ASC;", nativeQuery = true)
	List<Category> mostUsedCategory();

}
