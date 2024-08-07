package com.JFSEI.Storage_Management_System.api.dtos.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryOutputResponse {
    private Long id;
    private String name;
    private String reference;
}
