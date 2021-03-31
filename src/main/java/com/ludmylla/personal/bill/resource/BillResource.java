package com.ludmylla.personal.bill.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ludmylla.personal.bill.mapper.BillMapper;
import com.ludmylla.personal.bill.model.Bill;
import com.ludmylla.personal.bill.model.dto.BillCreateDto;
import com.ludmylla.personal.bill.service.BillService;

@RestController
@RequestMapping("/bill")
public class BillResource {
	
	@Autowired
	private BillService billService;
	
	@PostMapping
	public ResponseEntity<String> createBill(@RequestBody BillCreateDto billCreateDto){
		Bill bill = BillMapper.INSTANCE.toBillCreateDto(billCreateDto);
		billService.save(bill);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(" Bill successfully registered!  ");
	}

}
