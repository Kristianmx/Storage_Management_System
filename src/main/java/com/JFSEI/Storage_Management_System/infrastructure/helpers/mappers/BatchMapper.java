package com.JFSEI.Storage_Management_System.infrastructure.helpers.mappers;

import com.JFSEI.Storage_Management_System.api.dtos.request.Check_inRequest;
import com.JFSEI.Storage_Management_System.api.dtos.response.Check_inResponse;
import com.JFSEI.Storage_Management_System.domain.entities.Check_in;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BatchMapper extends GenericMapper<Check_inRequest, Check_inResponse, Check_in> {
}
