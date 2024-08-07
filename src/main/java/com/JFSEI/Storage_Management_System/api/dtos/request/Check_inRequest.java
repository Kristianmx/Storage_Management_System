package com.JFSEI.Storage_Management_System.api.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Check_inRequest {
    @NotNull(message = "incomingQuantity is required")
    private  Integer incomingQuantity;
    @NotNull(message = "Inventory name is required")
    private  Long idInventory;

    @NotBlank(message = "delivery is required")
    private String delivery;
}