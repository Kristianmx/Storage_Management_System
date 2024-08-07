package com.JFSEI.Storage_Management_System.infrastructure.abstract_services;

import com.JFSEI.Storage_Management_System.api.dtos.request.OutputRecordRequest;
import com.JFSEI.Storage_Management_System.api.dtos.response.OutputRecordResponse;
import com.JFSEI.Storage_Management_System.domain.entities.OutputRecord;

public interface IOutputService extends GenericService<OutputRecordRequest, OutputRecordResponse,Long>{
}
