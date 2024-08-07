package com.JFSEI.Storage_Management_System.api.dtos.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Check_inResponse {
    private Long id;
    private LocalDateTime entryDate;
    private Integer incomingQuantity;
    private String delivery;

}
