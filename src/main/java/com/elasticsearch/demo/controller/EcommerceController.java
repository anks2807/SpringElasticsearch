package com.elasticsearch.demo.controller;

import com.elasticsearch.demo.DTO.ReturnMessageDto;
import com.elasticsearch.demo.service.EcommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EcommerceController {

    @Autowired
    private EcommerceService ecommerceService;

    @PostMapping(value = "/ingest-ecommerce-data")
    public ReturnMessageDto ingestEcommerceData() {
        return ecommerceService.ingestBulkData();
    }
}
