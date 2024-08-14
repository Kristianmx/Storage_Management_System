package com.JFSEI.Storage_Management_System.infrastructure.Services;

import com.JFSEI.Storage_Management_System.api.dtos.request.Check_inRequest;
import com.JFSEI.Storage_Management_System.api.dtos.response.Check_inResponse;
import com.JFSEI.Storage_Management_System.domain.entities.Check_in;
import com.JFSEI.Storage_Management_System.domain.entities.Inventory;
import com.JFSEI.Storage_Management_System.domain.repositories.Check_inRepository;
import com.JFSEI.Storage_Management_System.domain.repositories.InventoryRepository;
import com.JFSEI.Storage_Management_System.infrastructure.abstract_services.ICheck_inService;
import com.JFSEI.Storage_Management_System.infrastructure.helpers.SupportService;
import com.JFSEI.Storage_Management_System.infrastructure.helpers.mappers.BatchMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class Check_inService implements ICheck_inService {

    @Autowired
    private final SupportService<Inventory> inventorySupport;
    @Autowired
    private final InventoryRepository inventoryRepository;
    @Autowired
    private final Check_inRepository check_inRepository;
    @Autowired
    private final BatchMapper batchMapper;
    @Autowired
    private final SupportService<Check_in> batchSupport;

    @Override
    public Check_inResponse create(Check_inRequest batchRequest) {
        Check_in checkin =this.batchMapper.toEntity(batchRequest);
        checkin.setEntryDate(LocalDateTime.now());
        Inventory inventory =this.inventorySupport.findById(inventoryRepository,batchRequest.getIdInventory(),"inventory");
        checkin.setInventory(inventory);
        checkin = this.check_inRepository.save(checkin);
        if (checkin.getId()!=null){
            inventory.setQuantity(inventory.getQuantity()+ checkin.getIncomingQuantity());
            this.inventoryRepository.save(inventory);
        }

        return this.batchMapper.toResponse(checkin);
    }


    @Override
    public List<Check_inResponse> getAll() {
        return this.batchMapper.toListResponse(this.check_inRepository.findAll());
    }

    @Override
    public Check_inResponse read(Long aLong) {
        return this.batchMapper.toResponse( this.batchSupport.findById(check_inRepository,aLong,"check-in"));
    }

    @Override
    public Check_inResponse update(Long aLong, Check_inRequest batchRequest) {
        Check_in checkin =this.batchSupport.findById(check_inRepository,aLong,"batch");
        Inventory inventory =this.inventorySupport.findById(inventoryRepository,checkin.getInventory().getId(),"Inventory");
        if (batchRequest.getIncomingQuantity()!= checkin.getIncomingQuantity()){
            Integer quantity= inventory.getQuantity()-checkin.getIncomingQuantity()+batchRequest.getIncomingQuantity();
            inventory.setQuantity(quantity);
            this.inventoryRepository.save(inventory);
        }

        BeanUtils.copyProperties(batchRequest, checkin);

        return this.batchMapper.toResponse(this.check_inRepository.save(checkin));
    }
}
