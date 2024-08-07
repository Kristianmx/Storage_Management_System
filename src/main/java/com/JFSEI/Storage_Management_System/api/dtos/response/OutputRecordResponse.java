package com.JFSEI.Storage_Management_System.api.dtos.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OutputRecordResponse {
    private Long id;
    private LocalDateTime outputDate;
    private  Integer outputQuantity;
    private String  delivered;
    private String projectName;
}
