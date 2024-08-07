package com.JFSEI.Storage_Management_System.api.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRequest {
    @NotBlank(message = " name is required")
    private String name;
    @NotBlank(message = "reference is required")
    private String reference;
    @NotBlank(message = "description is required")
    private String description;
    @NotNull(message = "quantity is required")
    private  Integer quantity;
}
