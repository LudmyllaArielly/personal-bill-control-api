package com.ludmylla.personal.bill.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ludmylla.personal.bill.mapper.PayMapper;
import com.ludmylla.personal.bill.model.Pay;
import com.ludmylla.personal.bill.model.dto.PayInsertAndListAllDto;
import com.ludmylla.personal.bill.model.dto.PayUpdateDto;
import com.ludmylla.personal.bill.service.PayService;

@RestController
public class PayResource {
	
	@Autowired
	private PayService payService;
	
	@PostMapping(path = "/payments")
	public ResponseEntity<String> createPay(@RequestBody PayInsertAndListAllDto payInsertAndListAllDto){
		try {
			Pay pay = PayMapper.INSTANCE.toPayInsertAndListAllDto(payInsertAndListAllDto);
			payService.save(pay);
			return ResponseEntity.status(HttpStatus.CREATED).body(" Payment successfully created! ");
			
		}catch (DataIntegrityViolationException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(" Payment already exists: " + ex.getMessage());
		
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Failed to create payment: " + e.getMessage());
		}
	}
	
	@GetMapping(path = "/payments")
	public ResponseEntity<List<PayInsertAndListAllDto>> listAll() {
		try {
			List<Pay> payments = payService.listAll();
			List<PayInsertAndListAllDto> payInsertAndListAllDto = PayMapper.INSTANCE
					.dtoPayInsertAndListAllDto(payments);
			return new ResponseEntity<List<PayInsertAndListAllDto>>(payInsertAndListAllDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<PayInsertAndListAllDto>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/payments/{description}")
	public ResponseEntity<PayInsertAndListAllDto> findByDescription(@PathVariable("description") String description) {
		try {
			Pay payments = payService.findByDescription(description);
			PayInsertAndListAllDto payInsertAndListAllDto = PayMapper.INSTANCE.dtoPayInsertAndListAllDto(payments);

			return new ResponseEntity<PayInsertAndListAllDto>(payInsertAndListAllDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<PayInsertAndListAllDto>(HttpStatus.BAD_REQUEST);	
		}
	}
	
	@PutMapping(path = "/payments")
	public ResponseEntity<String> updatePay(@RequestBody PayUpdateDto payUpdateDto){
		try {
			Pay pay = PayMapper.INSTANCE.toPayUpdateDto(payUpdateDto);
			payService.update(pay);
			return ResponseEntity.status(HttpStatus.OK).body(" Payment updated successfully! ");
		
		}catch (DataIntegrityViolationException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(" Payment already exists: " + ex.getMessage());
		
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Payment update failed: " + e.getMessage());
		}
	}
	
	@DeleteMapping(path = "/payments")
	public ResponseEntity<String> deletePay(Long id){
		try {
			payService.delete(id);
			return ResponseEntity.status(HttpStatus.OK).body(" Successfully deleted! ");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Failed to delete payment: " + e.getMessage());
		}
	}
}
