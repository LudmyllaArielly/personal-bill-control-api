package com.ludmylla.personal.bill.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ludmylla.personal.bill.model.Solicitation;
import com.ludmylla.personal.bill.model.enums.Status;
import com.ludmylla.personal.bill.repository.SolicitationRepository;
import com.ludmylla.personal.bill.useful.Useful;

@Service
public class SolicitationServiceImpl implements SolicitationService {

	@Autowired
	private SolicitationRepository solicitationRepository;

	@Autowired
	private UserService userService;
	
	@Value("${com.zoneid}")
	private ZoneId zoneId;

	@Override
	public Long save(Solicitation solicitation) {
		validations(solicitation);
		solicitation.setStatus(Status.OPEN);
		solicitation.setSolicitationDate(ZonedDateTime.now(zoneId));
		solicitation.setUsername(userService.takesTheEmailOfTheUserLogin());
		Solicitation solicitationSave = solicitationRepository.save(solicitation);
		return solicitationSave.getId();
	}

	@Override
	public List<Solicitation> listAll() {
		validations();
		List<Solicitation> solicitations = solicitationRepository.findAllSolicitationOfUser();
		return solicitations;
	}

	@Override
	public List<Solicitation> findBySolicitationsOfUser() {
		validationsTokenIsNull();
		List<Solicitation> solicitations = solicitationRepository
				.findBySolicitationOfUser(userService.takesTheEmailOfTheUserLogin());
		return solicitations;
	}

	@Transactional
	@Modifying
	@Override
	public void update(Solicitation solicitation) {
		validationsUpdate(solicitation);
		Optional<Solicitation> solicitations = solicitationRepository.findById(solicitation.getId());
		solicitation.setSolicitationDate(solicitations.get().getSolicitationDate());
		solicitation.setDescription(solicitations.get().getDescription());
		solicitation.setUsername(solicitations.get().getUsername());
		solicitationRepository.save(solicitation);
	}

	@Override
	public void delete(Long id) {
		validations();
		validIfSolicitationExits(id);
		Optional<Solicitation> solicitation = solicitationRepository.findById(id);
		Solicitation solicitations = solicitation.get();
		solicitationRepository.delete(solicitations);
	}

	private void validations(Solicitation solicitation) {
		validationsTokenIsNull();
		validIfAttributesOfSolicitationIsNull(solicitation);
		validIfAttributesOfSolicitationIsBlank(solicitation);
		validIfAttributesOfUserContainsOrSpecialCharacters(solicitation);
	}

	private void validationsUpdate(Solicitation solicitation) {
		validationsTokenIsNullAndValidUserAccess();
		validIfStatusIsNull(solicitation);
		validIfSolicitationExits(solicitation);
	}

	private void validations() {
		validationsTokenIsNullAndValidUserAccess();
	}

	private void validationsTokenIsNull() {
		validIfTokenIsNull();
	}

	private void validationsTokenIsNullAndValidUserAccess() {
		validIfTokenIsNull();
		validUserAccess();
	}

	private void validIfTokenIsNull() {
		userService.validIfTokenIsNull();
	}

	private void validUserAccess() {
		userService.releasesAuthorizationForTheUser();
	}

	private void validIfAttributesOfSolicitationIsNull(Solicitation solicitation) {
		Useful.validIfAttributesIsNull(solicitation.getDescription());
	}

	private void validIfAttributesOfSolicitationIsBlank(Solicitation solicitation) {
		Useful.validIfAttributesAreBlank(solicitation.getDescription());
	}

	private void validIfStatusIsNull(Solicitation solicitation) {
		boolean isStatusNull = solicitation.getStatus() == null;
		if (isStatusNull) {
			throw new IllegalArgumentException("Status cannot be null.");
		}
	}
	
	private void validIfSolicitationExits(Solicitation solicitation) {
		Optional<Solicitation> solicitations = solicitationRepository.findById(solicitation.getId());
		boolean isSolicitationExits = solicitations.isEmpty();
		if (isSolicitationExits) {
			throw new IllegalArgumentException("Solicitation does not exist.");
		}
	}

	private void validIfSolicitationExits(Long id) {
		Optional<Solicitation> solicitations = solicitationRepository.findById(id);
		validIfSolicitationStatusIsOpenOptional(solicitations);
		boolean isSolicitationExits = solicitations.isEmpty();
		if (isSolicitationExits) {
			throw new IllegalArgumentException("Solicitation does not exist.");
		}
	}

	private void validIfAttributesOfUserContainsOrSpecialCharacters(Solicitation solicitation) {
		Useful.validIfItHasNumbersOrSpecialCharacters(solicitation.getDescription());
	}
	
	private void validIfSolicitationStatusIsOpenOptional(Optional<Solicitation> solicitations) {
		if(solicitations.get().getStatus().equals(Status.OPEN)) {
			throw new IllegalArgumentException("This action could not be performed because the status is open.");
		}
	}

}
