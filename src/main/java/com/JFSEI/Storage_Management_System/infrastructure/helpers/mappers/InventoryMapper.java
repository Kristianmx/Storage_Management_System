package com.JFSEI.Storage_Management_System.infrastructure.helpers.mappers;

import com.JFSEI.Storage_Management_System.api.dtos.request.InventoryRequest;
import com.JFSEI.Storage_Management_System.api.dtos.response.Check_inResponse;
import com.JFSEI.Storage_Management_System.api.dtos.response.InventoryResponse;
import com.JFSEI.Storage_Management_System.domain.entities.Check_in;
import com.JFSEI.Storage_Management_System.domain.entities.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InventoryMapper extends GenericMapper<InventoryRequest, InventoryResponse, Inventory> {
    Check_inResponse  ToCheckInResponse(Check_in checkIn);
    List<Check_inResponse> ToCheckInResponseList(List<Check_in> checkIns);
}
