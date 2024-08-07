package com.JFSEI.Storage_Management_System.infrastructure.abstract_services;

import com.JFSEI.Storage_Management_System.api.dtos.request.InventoryRequest;
import com.JFSEI.Storage_Management_System.api.dtos.response.InventoryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface IInventoryService extends GenericService<InventoryRequest, InventoryResponse,Long>{
     String disables(Long id);
     ByteArrayInputStream getXls() throws IOException;
     List<InventoryResponse> setXlsx(MultipartFile archive) throws IOException;
}
