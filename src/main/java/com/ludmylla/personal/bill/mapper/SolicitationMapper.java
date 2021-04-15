package com.ludmylla.personal.bill.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.ludmylla.personal.bill.model.Solicitation;
import com.ludmylla.personal.bill.model.dto.SolicitationCreateDto;
import com.ludmylla.personal.bill.model.dto.SolicitationListAllDto;
import com.ludmylla.personal.bill.model.dto.SolicitationUpdateStatusDto;

@Mapper
public interface SolicitationMapper {

	SolicitationMapper INSTANCE = Mappers.getMapper(SolicitationMapper.class);
	@Mappings({
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "solicitationDate", ignore = true),
		@Mapping(target = "status", ignore = true),
		@Mapping(target = "username", ignore = true)})
	Solicitation toSolicitation (SolicitationCreateDto source);
	
	@Mapping(target = "id", ignore = true)
	Solicitation toSolicitation (SolicitationListAllDto source);
	
	List<SolicitationListAllDto> dtoSolicitationListAllDto (List<Solicitation> source);
	@Mappings({
		@Mapping(target = "description", ignore = true),
		@Mapping(target = "solicitationDate", ignore = true),
		@Mapping(target = "username", ignore =  true)})
	Solicitation toSolicitation (SolicitationUpdateStatusDto source);
}
