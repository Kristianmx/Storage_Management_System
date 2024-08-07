package com.JFSEI.Storage_Management_System.api.dtos.response;

import com.JFSEI.Storage_Management_System.domain.entities.OutputRecord;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponse {
    private Long id;
    private String name;
    private String reference;
    private String description;
    private Integer quantity;
    private Boolean status;
    private List<Check_inResponse> check_in;
    private List<OutputRecordResponse> outputRecords;
}
