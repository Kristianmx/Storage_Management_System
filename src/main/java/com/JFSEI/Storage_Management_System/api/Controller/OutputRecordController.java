package com.JFSEI.Storage_Management_System.api.Controller;


import com.JFSEI.Storage_Management_System.api.dtos.request.OutputRecordRequest;
import com.JFSEI.Storage_Management_System.api.dtos.response.OutputRecordResponse;
import com.JFSEI.Storage_Management_System.infrastructure.abstract_services.IOutputService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/outputRecord")
@Tag(name= "OutputRecord")
@AllArgsConstructor
public class OutputRecordController {
    @Autowired
    private final IOutputService outputService;

    @PostMapping
    @Operation(
            summary = "Create outputRecord",
            description = "Fill in the required fields to create a new outputRecord."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "outputRecord retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    }
    )
    public ResponseEntity<OutputRecordResponse> create(@Validated @RequestBody OutputRecordRequest request) {
        return ResponseEntity.ok(this.outputService.create(request));
    }
    @ApiResponse(
            responseCode = "400", description = "ID not found"
    )
    @Operation(

            summary = "see outputRecord by id",
            description = "Write the ID of the outputRecord you are looking for."
    )
    @GetMapping(path = "/{id}")
    public ResponseEntity<OutputRecordResponse> read(@PathVariable Long id){
        return ResponseEntity.ok(this.outputService.read(id));
    }
    @ApiResponse(

            responseCode = "400", description = "ID not found"
    )
    @Operation(

            summary ="Update outputRecord",
            description = "update a outputRecord by entering the id and new information"
    )
    @PutMapping(path = "/{id}")
    public ResponseEntity<OutputRecordResponse> update(@RequestBody @Validated OutputRecordRequest request , @PathVariable Long id ){
        return ResponseEntity.ok(this.outputService.update(id,request));
    }
}
