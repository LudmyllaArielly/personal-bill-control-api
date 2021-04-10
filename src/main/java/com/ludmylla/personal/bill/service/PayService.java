package com.ludmylla.personal.bill.service;

import java.util.List;

import com.ludmylla.personal.bill.model.Pay;

public interface PayService {

	Long save(Pay pay);

	List<Pay> listAll();

	Pay findByDescription(String description);

	void validIfPayIsNull(Pay pay);

	Long update(Pay pay);

	void delete(Long id);
}
