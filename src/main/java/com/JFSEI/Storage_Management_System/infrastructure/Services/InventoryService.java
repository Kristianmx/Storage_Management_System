package com.JFSEI.Storage_Management_System.infrastructure.Services;

import com.JFSEI.Storage_Management_System.api.dtos.request.InventoryRequest;
import com.JFSEI.Storage_Management_System.api.dtos.response.InventoryResponse;
import com.JFSEI.Storage_Management_System.domain.entities.Check_in;
import com.JFSEI.Storage_Management_System.domain.entities.Inventory;
import com.JFSEI.Storage_Management_System.domain.entities.OutputRecord;
import com.JFSEI.Storage_Management_System.domain.repositories.Check_inRepository;
import com.JFSEI.Storage_Management_System.domain.repositories.InventoryRepository;
import com.JFSEI.Storage_Management_System.domain.repositories.OutputRecordRepository;
import com.JFSEI.Storage_Management_System.infrastructure.abstract_services.IInventoryService;
import com.JFSEI.Storage_Management_System.infrastructure.helpers.SupportService;
import com.JFSEI.Storage_Management_System.infrastructure.helpers.mappers.InventoryMapper;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InventoryService implements IInventoryService {
    @Autowired
    private final InventoryRepository inventoryRepository;
    @Autowired
    private final SupportService<Inventory> supportInventory;
    @Autowired
    private final InventoryMapper inventoryMapper;
    @Autowired
    private final Check_inRepository checkinRepository;
    @Autowired
    private final OutputRecordRepository outputRecordRepository;

    @Override
    public InventoryResponse create(InventoryRequest inventoryRequest) {
        Inventory inventory = this.inventoryMapper.toEntity(inventoryRequest);
        inventory.setStatus(true);
        inventory = this.inventoryRepository.save(inventory);

        Check_in checkin = new Check_in();
        checkin.setEntryDate(LocalDateTime.now());
        checkin.setIncomingQuantity(inventory.getQuantity());
        checkin.setInventory(inventory);
        checkin.setDelivery("JF SEI");
        checkin.setStatus(true);
        checkin.setObservation("*");

        List<Check_in> checkins = new ArrayList<>();
        checkins.add(this.checkinRepository.save(checkin));
        inventory.setCheck_in(checkins);
        inventory.setOutputRecords(new ArrayList<>());


        return  this.inventoryMapper.toResponse(inventory);


    }

    @Override
    public List<InventoryResponse> getAll() {
    return this.inventoryMapper.toListResponse(this.inventoryRepository.findAll());
    }

    @Override
    public InventoryResponse read(Long aLong) {
        return this.inventoryMapper.toResponse(this.supportInventory.findById(this.inventoryRepository,aLong,"inventory"));
    }

    @Override
    public InventoryResponse update(Long aLong, InventoryRequest inventoryRequest) {
        Inventory inventory =this.supportInventory.findById(this.inventoryRepository,aLong,"Inventory");
        BeanUtils.copyProperties(inventory,inventoryRequest);
        return this.inventoryMapper.toResponse(this.inventoryRepository.save(inventory));
    }

    @Override
    public String disables(Long id) {
        Inventory inventory =this.supportInventory.findById(this.inventoryRepository,id,"Inventory");
        inventory.setStatus(false);
        this.inventoryRepository.save(inventory);
        return "the item was disabled";
    }

    @Override
    public ByteArrayInputStream getXls() throws IOException {
        List<Inventory> inventories =this.inventoryRepository.findAll();
        try(Workbook workbook= new XSSFWorkbook()){
            Sheet sheet = workbook.createSheet("Inventory");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Name");
            header.createCell(2).setCellValue("reference");
            header.createCell(3).setCellValue("description");
            header.createCell(4).setCellValue("status");
            header.createCell(5).setCellValue("quantity");
            header.createCell(6).setCellValue("check_in");
            header.createCell(7).setCellValue("outputRecord");
            int rowIdx =1;
            for (Inventory inventory : inventories){
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(inventory.getId());
                row.createCell(1).setCellValue(inventory.getName());
                row.createCell(2).setCellValue(inventory.getReference());
                row.createCell(3).setCellValue(inventory.getDescription());
                row.createCell(4).setCellValue(inventory.getStatus());
                row.createCell(5).setCellValue(inventory.getQuantity());
                  String batch;
                StringBuilder batchBuilder = new StringBuilder();
                inventory.getCheck_in().forEach(i->{
                    batchBuilder.append( i.getEntryDate()).append(",").append(i.getIncomingQuantity()).append("/");
                });
                    batch = batchBuilder.toString();
                row.createCell(6).setCellValue(batch);
                String outputRecords;
                StringBuilder outputRecordsBuilder = new StringBuilder();
                inventory.getOutputRecords().forEach(i->{
                    outputRecordsBuilder.append( i.getOutputDate()).append(",").append(i.getOutputQuantity()).append("/");
                });
                outputRecords = outputRecordsBuilder.toString();
                row.createCell(7).setCellValue(outputRecords);
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    @Override
    public List<InventoryResponse> setXlsx(MultipartFile archive)  throws IOException{

        List<Inventory> inventoryList = new ArrayList<>();
        try(Workbook workbook = new XSSFWorkbook(archive.getInputStream())){
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            int conta=0;
            while (rowIterator.hasNext()){
                Row row = rowIterator.next();
                Cell[] info = new Cell[8];
                Inventory inventory = new Inventory();
                if (conta!=0 && conta!=1 && conta!=2){
                    for (int ite =0;ite <8;ite++){
                        info[ite]=row.getCell(ite);
                    }
                    inventory.setName(info[1].getStringCellValue());
                    inventory.setReference(info[2].getStringCellValue());
                    inventory.setDescription(info[3].getStringCellValue());
                    inventory.setStatus(info[4].getBooleanCellValue());
                    inventory.setQuantity(info[5].getColumnIndex());
                    inventory= this.inventoryRepository.save(inventory);
                    String[] partBySlash= info[6].getStringCellValue().split("/");
                    int batchs = 0;
                    for(String part: partBySlash){
                        String[] subpart = part.split(",");
                        Check_in checkin = new Check_in();
                        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                        checkin.setEntryDate(LocalDateTime.parse(subpart[0],formatter));
                        checkin.setIncomingQuantity(Integer.parseInt(subpart[1]));
                        batchs+=Integer.parseInt(subpart[1]);
                        checkin.setInventory(inventory);
                        this.checkinRepository.save(checkin);

                    }
                    String[] OutPart= info[7].getStringCellValue().split("/");
                    int out=0;
                    for(String part: OutPart){
                        String[] subpart = part.split(",");
                        OutputRecord outputRecord = new OutputRecord();
                        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                        outputRecord.setOutputDate(LocalDateTime.parse(subpart[0],formatter));
                        out+=Integer.parseInt(subpart[1]);
                        outputRecord.setInventory(inventory);
                        outputRecord.setOutputQuantity(Integer.parseInt(subpart[1]));
                        this.outputRecordRepository.save(outputRecord);
                    }
                    inventory.setQuantity(batchs-out);
                    inventoryList.add(this.inventoryRepository.save(inventory));
                }
                conta++;
            }
        }
        return  inventoryList.stream().map(this::convert).collect(Collectors.toList());
    }
    public InventoryResponse convert(Inventory inventory){
        return this.inventoryMapper.toResponse(inventory);
    }

}
