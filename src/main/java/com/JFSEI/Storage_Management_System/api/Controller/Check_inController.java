package com.JFSEI.Storage_Management_System.api.Controller;

import com.JFSEI.Storage_Management_System.api.dtos.request.Check_inRequest;
import com.JFSEI.Storage_Management_System.api.dtos.response.Check_inResponse;
import com.JFSEI.Storage_Management_System.infrastructure.abstract_services.ICheck_inService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/Check_in")
@Tag(name= "Check_in")
@AllArgsConstructor
public class Check_inController {
    @Autowired
    private final ICheck_inService batchService;
    @PostMapping
    @Operation(
            summary = "Create Check_in",
            description = "Fill in the required fields to create a new Check_in."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Check_in retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    }
    )
    public ResponseEntity<Check_inResponse> create(@Validated @RequestBody Check_inRequest request) {
        return ResponseEntity.ok(this.batchService.create(request));
    }

    @ApiResponse(
            responseCode = "400", description = "ID not found"
    )
    @Operation(

            summary = "see Check_in by id",
            description = "Write the ID of the Check_in you are looking for."
    )
    @GetMapping(path = "/{id}")
    public ResponseEntity<Check_inResponse> read(@PathVariable Long id) {
        return ResponseEntity.ok(this.batchService.read(id));
    }

    @ApiResponse(

            responseCode = "400", description = "ID not found"
    )
    @Operation(

            summary ="Update Check_in",
            description = "update a Check_in by entering the id and new information"
    )
    @PutMapping(path = "/{id}")
    public ResponseEntity<Check_inResponse> update(@RequestBody @Validated Check_inRequest request, @PathVariable  Long id) {
        return ResponseEntity.ok( this.batchService.update(id,request));
    }

    @Operation(

            summary = "All Check_in ",
            description = "brings all current checks"
    )
    @GetMapping
    public ResponseEntity<List <Check_inResponse>>readAll() {
        return ResponseEntity.ok(this.batchService.getAll());
    }
}
