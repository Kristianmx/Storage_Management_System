package com.JFSEI.Storage_Management_System.api.dtos.error;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseError {
    private String status;
    private Integer code;
    private Map<String, String> error;
}
