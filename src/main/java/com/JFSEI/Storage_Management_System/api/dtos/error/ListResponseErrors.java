package com.JFSEI.Storage_Management_System.api.dtos.error;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListResponseErrors {
    private String status;
    private Integer code;
    private List<Map<String,String>> errors;

}
