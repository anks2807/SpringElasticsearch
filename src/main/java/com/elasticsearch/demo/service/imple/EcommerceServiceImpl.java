package com.elasticsearch.demo.service.imple;


import com.elasticsearch.demo.DTO.ReturnMessageDto;
import com.elasticsearch.demo.documents.ECommerceData;
import com.elasticsearch.demo.service.EcommerceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EcommerceServiceImpl implements EcommerceService {

    private static final Logger logger = LoggerFactory.getLogger(EcommerceServiceImpl.class);
    @Value("${ecommerce.index}")
    private String eCommerceIndex;
    @Autowired
    private RestHighLevelClient elasticsearchClient;
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private ObjectMapper objectMapper;

    private static List<ECommerceData> loadData(File file) {
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy H:m");
        List<ECommerceData> eCommerceDtos = null;
        try (
                BufferedReader br = new BufferedReader(new FileReader(file));
                CSVParser parser = CSVFormat.DEFAULT.withDelimiter(',').withHeader().parse(br);
        ) {
            eCommerceDtos = new ArrayList<>();
            for (CSVRecord record : parser) {
                ECommerceData eCommerceDto = new ECommerceData();
                eCommerceDto.setCountry(record.get("Country"));
                eCommerceDto.setInvoiceNo(record.get("InvoiceNo"));
                eCommerceDto.setQuantity(record.get("Quantity") == null || record.get("Quantity").length() == 0 ? 0 : Long.parseLong(record.get("Quantity")));
                eCommerceDto.setDescription(record.get("Description"));
                eCommerceDto.setCustomerID(record.get("CustomerID") == null || record.get("CustomerID").length() == 0 ? 0 : Long.parseLong(record.get("CustomerID")));
                eCommerceDto.setStockCode(record.get("StockCode"));
                eCommerceDto.setUnitPrice(record.get("UnitPrice") == null || record.get("UnitPrice").length() == 0 ? 0 : Double.parseDouble(record.get("UnitPrice")));
                eCommerceDto.setInvoiceDate(sdf.parse(record.get("InvoiceDate")));
                eCommerceDtos.add(eCommerceDto);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return eCommerceDtos;
    }

    @Override
    public ReturnMessageDto ingestBulkData() {
        List<ECommerceData> eCommerceDtos = null;
        try {
            eCommerceDtos = loadData(resourceLoader.getResource("classpath:ecommerce_data.csv").getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BulkRequest bulkRequest = new BulkRequest();
        int startIndex = 0;
        int batchSize = 10000;
        int endIndex = batchSize;
        while (endIndex <= eCommerceDtos.size()) {
            for (int i = startIndex; i < endIndex; i++) {
                IndexRequest indexRequest = new IndexRequest(eCommerceIndex).id(String.valueOf(i)).source(objectMapper.convertValue(eCommerceDtos.get(i), Map.class));
                bulkRequest.add(indexRequest);
            }


            try {
                elasticsearchClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            } catch (Exception exception) {
                logger.error(exception.getMessage());
            }
            if (eCommerceDtos.size() - endIndex == 0) {
                break;
            }
            if (eCommerceDtos.size() - endIndex < batchSize) {
                endIndex = eCommerceDtos.size();
                continue;
            }
            startIndex = startIndex + batchSize;
            endIndex = endIndex + batchSize;
        }

        return new ReturnMessageDto("Data ingested successfully", 200);
    }

}
