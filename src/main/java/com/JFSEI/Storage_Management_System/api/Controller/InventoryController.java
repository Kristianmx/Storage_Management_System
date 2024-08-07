package com.JFSEI.Storage_Management_System.api.Controller;

import com.JFSEI.Storage_Management_System.api.dtos.request.InventoryRequest;
import com.JFSEI.Storage_Management_System.api.dtos.response.InventoryResponse;
import com.JFSEI.Storage_Management_System.infrastructure.abstract_services.IInventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/inventories")
@Tag(name= "Inventory")
@AllArgsConstructor
public class InventoryController {
    @Autowired
    private final IInventoryService iInventoryService;

    @PostMapping
    @Operation(
            summary = "Create Item",
            description = "Fill in the required fields to create a new Item."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    }
    )
    public ResponseEntity<InventoryResponse> create(@Validated @RequestBody InventoryRequest request) {
        return ResponseEntity.ok(this.iInventoryService.create(request));
    }

    @ApiResponse(
            responseCode = "400", description = "ID not found"
    )
    @Operation(

            summary = "see item by id",
            description = "Write the ID of the item you are looking for."
    )
    @GetMapping(path = "/{id}")
    public ResponseEntity<InventoryResponse> read(@PathVariable Long id) {
        return ResponseEntity.ok(this.iInventoryService.read(id));
    }

    @ApiResponse(
            responseCode = "400", description = "ID not found"
    )
    @Operation(

            summary ="Update item",
            description = "update a item by entering the id and new information"
    )
    @PutMapping(path = "/{id}")
    public ResponseEntity<InventoryResponse> update(@RequestBody @Validated InventoryRequest request, @PathVariable  Long id) {
        return ResponseEntity.ok( this.iInventoryService.update(id,request));
    }
    @Operation(
            summary = "disable Item ",
            description = "Enter the id of the item to disable."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping(path = "/{id}")
    public  ResponseEntity<String> disable(@PathVariable Long id){
        return ResponseEntity.ok(this.iInventoryService.disables(id));
    }

    @Operation(
            summary = "see all Items",
            description = "list of all Items."
    )
    @GetMapping
    public ResponseEntity<List<InventoryResponse>> getAll(){
        return ResponseEntity.ok(this.iInventoryService.getAll());
    }
    @Operation(
            summary = "export xlsx",
            description = "This endpoint returns you a .xlsx file."
    )
    @GetMapping(value = "/export-xlsx",produces = MediaType.APPLICATION_ATOM_XML_VALUE)
    public ResponseEntity<byte[]> getXml() throws IOException {
        ByteArrayInputStream in = this.iInventoryService.getXls();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition","attachment; filename=Product.xlsx");
        return  ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM).body(in.readAllBytes());
    }
    @Operation(
            summary = "import archive.xlsx",
            description = "Upload an Excel file in xlsx format for reading and content creation"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid page or size parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(path = "/import-xlsx",consumes = "multipart/form-data")
    public ResponseEntity<List<InventoryResponse>> importXlsx(@RequestParam("archivo Xlsx") MultipartFile archivoXlsx) throws IOException{

        return ResponseEntity.ok(this.iInventoryService.setXlsx(archivoXlsx));
    }
}
