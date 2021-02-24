package com.ludmylla.personal.bill.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ludmylla.personal.bill.mapper.SolicitationMapper;
import com.ludmylla.personal.bill.model.Solicitation;
import com.ludmylla.personal.bill.model.dto.SolicitationInsertDto;
import com.ludmylla.personal.bill.model.dto.SolicitationListAllDto;
import com.ludmylla.personal.bill.model.dto.SolicitationUpdateStatusDto;
import com.ludmylla.personal.bill.service.SolicitationService;

@RestController
@RequestMapping("/solicitations")
public class SolicitationResource {
	
	@Autowired
	private SolicitationService solicitationService;
	
	@PostMapping
	public ResponseEntity<String> saveSolicitation(@RequestBody SolicitationInsertDto solicitationInsertDto){
		try {
			Solicitation solicitation = SolicitationMapper.INSTANCE.toSolicitationInsertDto(solicitationInsertDto);
			solicitationService.save(solicitation);
			
			return ResponseEntity.status(HttpStatus.CREATED).body("Solicitation created!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Failed to create solicitation: " + e.getMessage());
	  }
	}
	
	@GetMapping("/listAll")
	public ResponseEntity<List<SolicitationListAllDto>> listAll(){
		try {
			List<Solicitation> solicitations = solicitationService.listAll();
			List<SolicitationListAllDto> solicitationListAllDtos = SolicitationMapper
					.INSTANCE.dtoSolicitationListAllDto(solicitations);
			return new ResponseEntity<List<SolicitationListAllDto>>(solicitationListAllDtos,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<SolicitationListAllDto>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping
	public ResponseEntity<List<SolicitationListAllDto>> findBySolicitationOfUser(){
		try {
			List<Solicitation> solicitations = solicitationService.findBySolicitationsOfUser();
			List<SolicitationListAllDto> solicitationListAllDtos = SolicitationMapper
					.INSTANCE.dtoSolicitationListAllDto(solicitations);
			return new ResponseEntity<List<SolicitationListAllDto>>(solicitationListAllDtos,HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<List<SolicitationListAllDto>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<String> updateSolicitation(@RequestBody SolicitationUpdateStatusDto solicitationUpdateStatusDto){
		try {
			Solicitation solicitation = SolicitationMapper.INSTANCE
					.toSolicitationUpdateStatusDto(solicitationUpdateStatusDto);
			solicitationService.update(solicitation);
			return ResponseEntity.status(HttpStatus.OK).body("Solicitation updated!");
			
		}catch (HttpMessageNotReadableException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Status cannot be empty and must have the keyword OPEN, CLOSED, CANCELED: " + ex.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Failed to update solicitation: " + e.getMessage());
		}
	}
		
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteSolicitation(Long id){
		try {
			solicitationService.delete(id);
			return ResponseEntity.status(HttpStatus.OK).body(" Successfully deleted! ");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Failed to delete solicitation: " + e.getMessage());
		}
	}	
}
