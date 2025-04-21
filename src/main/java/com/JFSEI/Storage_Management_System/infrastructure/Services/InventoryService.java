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
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
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
        if (inventoryRequest.getQuantity() != inventory.getQuantity()){
            if (inventory.getCheck_in().size()==1) {
                inventory.getCheck_in().forEach(checkIn -> {
                        if (checkIn.getIncomingQuantity()==inventory.getQuantity()){
                            checkIn.setIncomingQuantity(inventoryRequest.getQuantity());
                            this.checkinRepository.save(checkIn);
                        }
                });
            }
        }

        BeanUtils.copyProperties(inventoryRequest,inventory);
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
            CellStyle style = workbook.createCellStyle();
            style.setWrapText(true);
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            Font greenFont = workbook.createFont();
            greenFont.setColor(IndexedColors.GREEN.getIndex());

            CellStyle greenTextStyle = workbook.createCellStyle();
            greenTextStyle.setWrapText(true);
            greenTextStyle.setAlignment(HorizontalAlignment.CENTER);
            greenTextStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            greenTextStyle.setFont(greenFont);
            greenTextStyle.setWrapText(true);
            for (int i = 1; i <= 3; i++) {
                Row header = sheet.createRow(i-1);

                if (i==1){
                    sheet.addMergedRegion(new CellRangeAddress(i - 1, i - 1, 0, 5));
                    Cell cell = header.createCell(0);
                    cell.setCellValue("Nota :    En este excel no debe de tener casillas sin contenido exceptuando la columna (ID),  en caso de no tener contenido  colocar: Para  casillas de texto ( N/A )  ---- Para casillas  numericas o decimales ( 0 )  Se pueden agregar mas columnas  para ello:    La primera letra debe de ser mayuscula  Y  No debe llevar acento  \"Descripción\" \" Descripcion\"     Si necesita asistencia contactar con quien le paso este formato");
                    cell.setCellStyle(style);
                    header.setHeightInPoints(80);


                } else if (i==2) {
                    Cell cell0 = header.createCell(0);
                    cell0.setCellValue("Este campo no se rellena.");
                    cell0.setCellStyle(style);

                    Cell cell1 = header.createCell(1);
                    cell1.setCellValue("El nombre es obligatorio");
                    cell1.setCellStyle(style);

                    Cell cell2 = header.createCell(2);
                    cell2.setCellValue("En caso de no tener referercia. Colocar ( N/A )");
                    cell2.setCellStyle(style);

                    Cell cell3 = header.createCell(3);
                    cell3.setCellValue("En caso de no tener Descripción. Colocar ( N/A )");
                    cell3.setCellStyle(style);

                    Cell cell4 = header.createCell(4);
                    cell4.setCellValue("El estado no se rellena");
                    cell4.setCellStyle(style);

                    Cell cell5 = header.createCell(5);
                    cell5.setCellValue("En caso de no tener Precio. Colocar ( 0 )");
                    cell5.setCellStyle(style);

                } else {
                    Cell cell0 = header.createCell(0);
                    cell0.setCellValue("ID");
                    cell0.setCellStyle(greenTextStyle);

                    Cell cell1 = header.createCell(1);
                    cell1.setCellValue("Name");
                    cell1.setCellStyle(greenTextStyle);

                    Cell cell2 = header.createCell(2);
                    cell2.setCellValue("reference");
                    cell2.setCellStyle(greenTextStyle);

                    Cell cell3 = header.createCell(3);
                    cell3.setCellValue("description");
                    cell3.setCellStyle(greenTextStyle);

                    Cell cell4 = header.createCell(4);
                    cell4.setCellValue("status");
                    cell4.setCellStyle(greenTextStyle);

                    Cell cell5 = header.createCell(5);
                    cell5.setCellValue("quantity");
                    cell5.setCellStyle(greenTextStyle);
                }

            }
            for (int colIdx = 0; colIdx < 6; colIdx++) {
                sheet.setColumnWidth(colIdx, 4000); //
            }

            int rowIdx =3;
            for (Inventory inventory : inventories){
                Row row = sheet.createRow(rowIdx++);
                row.setHeightInPoints(20); // Ajustar la altura de la fila

                Cell cell0 = row.createCell(0);
                cell0.setCellValue(inventory.getId());
                cell0.setCellStyle(style);

                Cell cell1 = row.createCell(1);
                cell1.setCellValue(inventory.getName());
                cell1.setCellStyle(style);

                Cell cell2 = row.createCell(2);
                cell2.setCellValue(inventory.getReference());
                cell2.setCellStyle(style);

                Cell cell3 = row.createCell(3);
                cell3.setCellValue(inventory.getDescription());
                cell3.setCellStyle(style);

                Cell cell4 = row.createCell(4);
                cell4.setCellValue(inventory.getStatus());
                cell4.setCellStyle(style);

                Cell cell5 = row.createCell(5);
                cell5.setCellValue(inventory.getQuantity());
                cell5.setCellStyle(style);

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
                Cell[] info = new Cell[6];
                Inventory inventory = new Inventory();
                if (conta!=0 && conta!=1 && conta!=2){
                    for (int ite =0;ite <6;ite++){
                        info[ite]=row.getCell(ite);
                    }
                    inventory.setName(info[1].getStringCellValue());
                    inventory.setReference(info[2].getStringCellValue());
                    inventory.setDescription(info[3].getStringCellValue());


                    if (info[4] ==null){
                        inventory.setStatus(true);
                    }else {
                        inventory.setStatus(info[4].getBooleanCellValue());
                    }

                    inventory.setQuantity((int)
                            info[5].getNumericCellValue());
                    inventory.setOutputRecords(new ArrayList<>());
                    inventory.setCheck_in(new ArrayList<>());
                    List<Check_in> checkInList =new ArrayList<>();
                    inventory= this.inventoryRepository.save(inventory);
                    if (inventory.getId()!=null){
                        Check_in checkin = new Check_in();
                        checkin.setEntryDate(LocalDateTime.now());
                        checkin.setIncomingQuantity(inventory.getQuantity());
                        checkin.setInventory(inventory);
                        checkin.setDelivery("GPI");
                        checkin.setStatus(true);
                        checkin.setObservation("*");
                        this.checkinRepository.save(checkin);
                        checkInList.add(checkin);
                    }
                    inventory.setCheck_in(checkInList);
                inventoryList.add(inventory);
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
