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
	
	@Query(value = "select c.*, count(b.category_id) from category as c"
			+ " join bill as b on c.id=b.category_id group by c.id order by c.id desc;", nativeQuery = true)
	List<Category> mostUsedCategory();

}
