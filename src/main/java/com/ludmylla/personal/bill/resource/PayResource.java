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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ludmylla.personal.bill.mapper.PayMapper;
import com.ludmylla.personal.bill.model.Pay;
import com.ludmylla.personal.bill.model.dto.PayCreateAndListAllDto;
import com.ludmylla.personal.bill.model.dto.PayUpdateDto;
import com.ludmylla.personal.bill.service.PayService;

@RestController
@RequestMapping("/payments")
public class PayResource {
	
	@Autowired
	private PayService payService;
	
	@PostMapping
	public ResponseEntity<String> createPay(@RequestBody PayCreateAndListAllDto payCreateAndListAllDto) throws DataIntegrityViolationException{
		try {
			Pay pay = PayMapper.INSTANCE.toPay(payCreateAndListAllDto);
			payService.save(pay);
			return ResponseEntity.status(HttpStatus.CREATED).body(" Payment successfully created! ");		
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Failed to create payment: " + e.getMessage());
		}
	}
	
	@GetMapping
	public ResponseEntity<List<PayCreateAndListAllDto>> listAll() {
		try {
			List<Pay> payments = payService.listAll();
			List<PayCreateAndListAllDto> payCreateAndListAllDto = PayMapper.INSTANCE
					.dtoPayCreateAndListAllDto(payments);
			return new ResponseEntity<List<PayCreateAndListAllDto>>(payCreateAndListAllDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<PayCreateAndListAllDto>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{description}")
	public ResponseEntity<PayCreateAndListAllDto> findByDescription(@PathVariable("description") String description) {
		try {
			Pay payments = payService.findByDescription(description);
			PayCreateAndListAllDto payCreateAndListAllDto = PayMapper.INSTANCE.dtoPayCreateAndListAllDto(payments);

			return new ResponseEntity<PayCreateAndListAllDto>(payCreateAndListAllDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<PayCreateAndListAllDto>(HttpStatus.BAD_REQUEST);	
		}
	}
	
	@PutMapping
	public ResponseEntity<String> updatePay(@RequestBody PayUpdateDto payUpdateDto){
		try {
			Pay pay = PayMapper.INSTANCE.toPay(payUpdateDto);
			payService.update(pay);
			return ResponseEntity.status(HttpStatus.OK).body(" Payment updated successfully! ");
		
		}catch (DataIntegrityViolationException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(" Payment already exists: " + ex.getMessage());
		
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Payment update failed: " + e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePay(@PathVariable("id") Long id){
		try {
			payService.delete(id);
			return ResponseEntity.status(HttpStatus.OK).body(" Successfully deleted! ");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Failed to delete payment: " + e.getMessage());
		}
	}
}
