package com.elasticsearch.demo.service;

import com.elasticsearch.demo.DTO.ReturnMessageDto;
import com.elasticsearch.demo.documents.ECommerceData;
import org.springframework.stereotype.Service;


public interface EcommerceService {
    ReturnMessageDto ingestBulkData();
}
