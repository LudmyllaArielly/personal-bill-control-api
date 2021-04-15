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
	private String zoneId;

	@Override
	public Long save(Solicitation solicitation) {
		validationsSave(solicitation);
		solicitation.setStatus(Status.OPEN);
		solicitation.setSolicitationDate(ZonedDateTime.now(ZoneId.of(zoneId)));
		solicitation.setUsername(userService.takesTheEmailOfTheUserLogin());
		Solicitation solicitationSave = solicitationRepository.save(solicitation);
		return solicitationSave.getId();
	}

	@Override
	public List<Solicitation> listAll() {
		validIfTokenIsNullAndValidUserAccess();
		List<Solicitation> solicitations = solicitationRepository.findAllSolicitationOfUser();
		return solicitations;
	}

	@Override
	public List<Solicitation> findsAllUserSolicitation() {
		validIfTokenIsNull();
		List<Solicitation> solicitations = solicitationRepository
				.findsAllUserSolicitation(userService.takesTheEmailOfTheUserLogin());
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
		validationsDelete(id);
		Optional<Solicitation> solicitation = solicitationRepository.findById(id);
		Solicitation solicitations = solicitation.get();
		solicitationRepository.delete(solicitations);
	}

	private void validationsSave(Solicitation solicitation) {
		validIfTokenIsNull();
		validIfAttributesOfSolicitationIsNull(solicitation);
		validIfAttributesOfSolicitationIsBlank(solicitation);
		validIfAttributesOfUserContainsOrSpecialCharacters(solicitation);
	}

	private void validationsUpdate(Solicitation solicitation) {
		validIfTokenIsNullAndValidUserAccess();
		validIfSolicitationExits(solicitation);
		validIfStatusIsNull(solicitation);
	}
	
	private void validationsDelete(Long id) {
		validIfTokenIsNullAndValidUserAccess();
		validIfSolicitationExits(id);
	}

	private void validIfTokenIsNullAndValidUserAccess() {
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
			throw new IllegalArgumentException("Status cannot be blank fields.");
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
		validIfSolicitationStatusIsOpen(solicitations);
		boolean isSolicitationExits = solicitations.isEmpty();
		if (isSolicitationExits) {
			throw new IllegalArgumentException("Solicitation does not exist.");
		}
	}

	private void validIfAttributesOfUserContainsOrSpecialCharacters(Solicitation solicitation) {
		Useful.validIfItHasNumbersOrSpecialCharacters(solicitation.getDescription());
	}
	
	private void validIfSolicitationStatusIsOpen(Optional<Solicitation> solicitations) {
		boolean isStatusOpen = solicitations.get().getStatus().equals(Status.OPEN);
		if(isStatusOpen) {
			throw new IllegalArgumentException("This action could not be performed because the status is open.");
		}
	}

}
