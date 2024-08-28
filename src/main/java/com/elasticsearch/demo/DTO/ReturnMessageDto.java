package com.elasticsearch.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReturnMessageDto {
    private String message;
    private int code;
}
