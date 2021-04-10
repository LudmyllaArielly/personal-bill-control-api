package com.ludmylla.personal.bill.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ludmylla.personal.bill.mapper.SolicitationMapper;
import com.ludmylla.personal.bill.model.Solicitation;
import com.ludmylla.personal.bill.model.dto.SolicitationCreateDto;
import com.ludmylla.personal.bill.model.dto.SolicitationListAllDto;
import com.ludmylla.personal.bill.model.dto.SolicitationUpdateStatusDto;
import com.ludmylla.personal.bill.service.SolicitationService;

@RestController
@RequestMapping("/solicitations")
public class SolicitationResource {
	
	@Autowired
	private SolicitationService solicitationService;
	
	@PostMapping
	public ResponseEntity<String> createSolicitation(@RequestBody SolicitationCreateDto solicitationCreateDto){
		try {
			Solicitation solicitation = SolicitationMapper.INSTANCE.toSolicitation(solicitationCreateDto);
			solicitationService.save(solicitation);
			
			return ResponseEntity.status(HttpStatus.CREATED).body("Solicitation created!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Failed to create solicitation: " + e.getMessage());
	  }
	}
	
	@GetMapping
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
	
	@GetMapping("/findsAllUserSolicitation")
	public ResponseEntity<List<SolicitationListAllDto>> findsAllUserSolicitation(){
		try {
			List<Solicitation> solicitations = solicitationService.findsAllUserSolicitation();
			List<SolicitationListAllDto> solicitationListAllDtos = SolicitationMapper
					.INSTANCE.dtoSolicitationListAllDto(solicitations);
			return new ResponseEntity<List<SolicitationListAllDto>>(solicitationListAllDtos,HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<List<SolicitationListAllDto>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PatchMapping
	public ResponseEntity<String> updateSolicitation(@RequestBody SolicitationUpdateStatusDto solicitationUpdateStatusDto){
		try {
			Solicitation solicitation = SolicitationMapper.INSTANCE
					.toSolicitation(solicitationUpdateStatusDto);
			solicitationService.update(solicitation);
			return ResponseEntity.status(HttpStatus.OK).body("Solicitation updated!");
			
		}catch (HttpMessageNotReadableException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Status cannot be empty and must have the keyword OPEN, CLOSED, CANCELED: " + ex.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Failed to update solicitation: " + e.getMessage());
		}
	}
		
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteSolicitation(@PathVariable("id") Long id){
		try {
			solicitationService.delete(id);
			return ResponseEntity.status(HttpStatus.OK).body(" Successfully deleted! ");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Failed to delete solicitation: " + e.getMessage());
		}
	}	
}
