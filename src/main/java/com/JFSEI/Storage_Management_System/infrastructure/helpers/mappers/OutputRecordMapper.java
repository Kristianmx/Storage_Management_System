package com.JFSEI.Storage_Management_System.infrastructure.helpers.mappers;

import com.JFSEI.Storage_Management_System.api.dtos.request.OutputRecordRequest;
import com.JFSEI.Storage_Management_System.api.dtos.response.OutputRecordResponse;
import com.JFSEI.Storage_Management_System.domain.entities.OutputRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OutputRecordMapper extends GenericMapper<OutputRecordRequest, OutputRecordResponse, OutputRecord> {
}
