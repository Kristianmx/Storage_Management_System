package com.JFSEI.Storage_Management_System.api.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OutputRecordRequest {
    @NotNull(message = "outputQuantity is required")
    private  Integer outputQuantity;
    @NotNull(message = "Inventory is required")
    private  Long idInventory;
    @NotBlank(message = "delivered is required")
    private String delivered;
    @NotBlank(message = "ProjectName is required")
    private String projectName;
    @NotNull(message = "condition  is required")
    private Boolean status;

    private String observation;
}
