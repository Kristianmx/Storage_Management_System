package com.JFSEI.Storage_Management_System.infrastructure.Services;

import com.JFSEI.Storage_Management_System.api.dtos.request.OutputRecordRequest;
import com.JFSEI.Storage_Management_System.api.dtos.response.OutputRecordResponse;
import com.JFSEI.Storage_Management_System.domain.entities.Inventory;
import com.JFSEI.Storage_Management_System.domain.entities.OutputRecord;
import com.JFSEI.Storage_Management_System.domain.repositories.InventoryRepository;
import com.JFSEI.Storage_Management_System.domain.repositories.OutputRecordRepository;
import com.JFSEI.Storage_Management_System.infrastructure.abstract_services.IOutputService;
import com.JFSEI.Storage_Management_System.infrastructure.helpers.SupportService;
import com.JFSEI.Storage_Management_System.infrastructure.helpers.mappers.OutputRecordMapper;
import com.JFSEI.Storage_Management_System.utils.exceptions.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@AllArgsConstructor
public class OutputRecordService implements IOutputService {
    @Autowired
    private final OutputRecordRepository outputRecordRepository;
    @Autowired
    private final OutputRecordMapper outputRecordMapper;
    @Autowired
    private final SupportService<OutputRecord>outputRecordSupport;
    @Autowired
    private final SupportService<Inventory> inventorySupport;
    @Autowired
    private final InventoryRepository inventoryRepository;
    @Override
    public OutputRecordResponse create(OutputRecordRequest outputRecordRequest) {
        OutputRecord outputRecord = this.outputRecordMapper.toEntity(outputRecordRequest);
        Inventory inventory = this.inventorySupport.findById(inventoryRepository,outputRecordRequest.getIdInventory(),"Inventory");
        outputRecord.setOutputDate(LocalDateTime.now());
        outputRecord.setInventory(inventory);
        if (inventory.getQuantity()-outputRecord.getOutputQuantity() <0){
            throw new BadRequestException("the amount cannot be negative");
        }

        inventory.setQuantity(inventory.getQuantity()-outputRecord.getOutputQuantity());
        this.inventoryRepository.save(inventory);

        return this.outputRecordMapper.toResponse(this.outputRecordRepository.save(outputRecord));
    }

    @Override
    public List<OutputRecordResponse> getAll() {
        return this.outputRecordMapper.toListResponse(this.outputRecordRepository.findAll());
    }

    @Override
    public OutputRecordResponse read(Long aLong) {
        return this.outputRecordMapper.toResponse(this.outputRecordSupport.findById(this.outputRecordRepository,aLong,"outputRecord"));
    }

    @Override
    public OutputRecordResponse update(Long aLong, OutputRecordRequest outputRecordRequest) {
        OutputRecord outputRecord = this.outputRecordSupport.findById(this.outputRecordRepository,aLong,"outputRecord");
        Inventory inventory = this.inventorySupport.findById(inventoryRepository,outputRecord.getInventory().getId(),"Inventory");
        if (outputRecordRequest.getOutputQuantity()!=outputRecord.getOutputQuantity()){
            Integer quantity= inventory.getQuantity()+outputRecord.getOutputQuantity()-outputRecordRequest.getOutputQuantity();
            inventory.setQuantity(quantity);
            this.inventoryRepository.save(inventory);
        }
        BeanUtils.copyProperties(outputRecordRequest,outputRecord);

        return this.outputRecordMapper.toResponse(this.outputRecordRepository.save(outputRecord));
    }
}
