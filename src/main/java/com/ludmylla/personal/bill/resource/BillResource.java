package com.ludmylla.personal.bill.resource;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ludmylla.personal.bill.configexcel.ExcelExporter;
import com.ludmylla.personal.bill.mapper.BillMapper;
import com.ludmylla.personal.bill.model.Bill;
import com.ludmylla.personal.bill.model.dto.BillCreateDto;
import com.ludmylla.personal.bill.model.dto.BillListAllDto;
import com.ludmylla.personal.bill.model.dto.BillUpdateDto;
import com.ludmylla.personal.bill.model.enums.AccountType;
import com.ludmylla.personal.bill.model.enums.ValueType;
import com.ludmylla.personal.bill.service.BillService;

@RestController
@RequestMapping("/bill")
public class BillResource {
	
	@Autowired
	private BillService billService;
	
	@PostMapping
	public ResponseEntity<String> createBill(@RequestBody BillCreateDto billCreateDto){	
		try {
			Bill bill = BillMapper.INSTANCE.toBill(billCreateDto);
			billService.save(bill);
			return ResponseEntity.status(HttpStatus.CREATED).body(" Bill successfully registered!  ");
		}  catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Failed bill registered!  " + e.getMessage());
		}
	}

	@PutMapping
	public ResponseEntity<String> updateBill(@RequestBody BillUpdateDto billUpdateDto){
		try {
			Bill bill = BillMapper.INSTANCE.toBill(billUpdateDto);
			billService.update(bill);
			return ResponseEntity.status(HttpStatus.OK).body("Account updated successfully!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update account: " + e.getMessage());
		}
	}
	
	@GetMapping
	public ResponseEntity<List<BillListAllDto>> listAll(){
		try {
			List<Bill> bill = billService.listAll();
			List<BillListAllDto> billListAllDto = BillMapper.INSTANCE
					.dtoBillListAllDto(bill);
			return new ResponseEntity<List<BillListAllDto>>(billListAllDto,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<BillListAllDto>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/findsAllUserAccounts")
	public ResponseEntity<List<BillListAllDto>> findsAllUserAccounts(){
		try {
			List<Bill> bill = billService.findsAllUserAccounts();
			List<BillListAllDto> billListAllDtos = BillMapper.INSTANCE
					.dtoBillListAllDto(bill);
			return new ResponseEntity<List<BillListAllDto>>(billListAllDtos,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<BillListAllDto>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/findBillByAttributes")
	public ResponseEntity<List<BillListAllDto>> findBillByAttributes(
			@RequestParam(required = false) String description,
			@RequestParam(required = false) String justification,
			@RequestParam(required = false ) BigDecimal priceTotal){		
		try {
			List<Bill> bill = billService.findBillByAttributes(description, justification, priceTotal);
			List<BillListAllDto> billListAllDtos = BillMapper.INSTANCE
					.dtoBillListAllDto(bill);
			return new ResponseEntity<List<BillListAllDto>>(billListAllDtos,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<BillListAllDto>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/findsInstallmentByDate")
	public ResponseEntity<List<BillListAllDto>> findBillPaymentInstallmentByDate(
			@RequestParam("dateEnd") @DateTimeFormat(pattern = "yyyy-MM-dd" )Date dateEnd,
			@RequestParam("dateStart") @DateTimeFormat(pattern = "yyyy-MM-dd" ) Date dateStart){		
		try {
			List<Bill> bill = billService.findBillPaymentInstallmentByDate(dateStart, dateEnd);
			List<BillListAllDto> billListAllDtos = BillMapper.INSTANCE
					.dtoBillListAllDto(bill);
			return new ResponseEntity<List<BillListAllDto>>(billListAllDtos,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<BillListAllDto>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/findBillByDate")
	public ResponseEntity<List<BillListAllDto>> findBillByDate(
			@RequestParam("dateEnd") @DateTimeFormat(pattern = "yyyy-MM-dd" )Date dateEnd,
			@RequestParam("dateStart") @DateTimeFormat(pattern = "yyyy-MM-dd" ) Date dateStart){		
		try {
			List<Bill> bill = billService.findBillByDate(dateStart, dateEnd);
			List<BillListAllDto> billListAllDtos = BillMapper.INSTANCE
					.dtoBillListAllDto(bill);
			return new ResponseEntity<List<BillListAllDto>>(billListAllDtos,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<BillListAllDto>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/findBillByAccountTypeAndValueType")
	public ResponseEntity<List<BillListAllDto>> findAccountTypeAndValueType(
			@RequestParam(required = false) AccountType accountType,
			@RequestParam(required = false) ValueType valueType){		
		try {
			List<Bill> bill = billService.findAccountTypeAndValueType(accountType, valueType);
			List<BillListAllDto> billListAllDtos = BillMapper.INSTANCE
					.dtoBillListAllDto(bill);
			return new ResponseEntity<List<BillListAllDto>>(billListAllDtos,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<BillListAllDto>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/exportsTheBillToExcel")
	public ResponseEntity<String> exportToExcel(HttpServletResponse response)throws IOException{
		try {
			response.setContentType("application/octet-stream");
			
			String headerKey = "Content-Disposition";
			String headerValue = "attachment; filename=Bill_info.xlsx";
			response.setHeader(headerKey, headerValue);
			
			List<Bill> listBill = billService.listAll();
			ExcelExporter excelExporter = new ExcelExporter(listBill);
			excelExporter.export(response);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteBill(@PathVariable("id") Long id){
		try {		
			billService.delete(id);	
			return ResponseEntity.status(HttpStatus.OK).body("Account successfully deleted! ");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Failed to delete account: " + e.getMessage());
		}
	}

}
