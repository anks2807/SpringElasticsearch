package com.elasticsearch.demo.service.imple;

import com.elasticsearch.demo.DTO.ECommerceDto;
import com.elasticsearch.demo.DTO.ReturnMessageDto;
import com.elasticsearch.demo.documents.ECommerceData;
import com.elasticsearch.demo.service.EcommerceService;
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
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class EcommerceServiceImpl implements EcommerceService {

    @Value("${ecommerce.index}")
    private String eCommerceIndex;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private static final Logger logger = LoggerFactory.getLogger(EcommerceServiceImpl.class);

    @Override
    public ReturnMessageDto ingestBulkData() {
        List<ECommerceData> eCommerceDtos = readCSV();
        BulkRequest bulkRequest = new BulkRequest();
        int startIndex = 0;
        int batchSize = 10000;
        int endIndex = batchSize;
        while(endIndex < eCommerceDtos.size()) {
            for(int i = startIndex; i < endIndex; i++) {
                IndexRequest indexRequest = new IndexRequest(eCommerceIndex, "_doc", String.valueOf(i));
                indexRequest.source(eCommerceDtos.get(i));
                bulkRequest.add(indexRequest);
            }

            try{
                restHighLevelClient.bulk(bulkRequest);
            }catch (IOException ioException) {
                logger.error(ioException.getMessage());
            }
            bulkRequest = new BulkRequest();

            if (eCommerceDtos.size() - endIndex < batchSize) {
                endIndex = eCommerceDtos.size() - endIndex;
                startIndex = eCommerceDtos.size() - startIndex;
            }
            startIndex = startIndex + batchSize;
            endIndex = endIndex + batchSize;
        }

        return new ReturnMessageDto("Data ingested successfully", 200);
    }

    private static List<ECommerceData> readCSV() {
        String path = "D:\\archive_1\\data.csv";
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy H:m");
        List<ECommerceData> eCommerceDtos = null;
        try (
                BufferedReader br = new BufferedReader(new FileReader(path));
                CSVParser parser = CSVFormat.DEFAULT.withDelimiter(',').withHeader().parse(br);
        ) {
            eCommerceDtos = new ArrayList<>();
            for (CSVRecord record : parser) {
                ECommerceData eCommerceDto = new ECommerceData();
                eCommerceDto.setCountry(record.get("Country"));
                eCommerceDto.setInvoiceNo(record.get("InvoiceNo"));
                eCommerceDto.setQuantity( record.get("Quantity") == null || record.get("Quantity").length() == 0 ? 0: Long.parseLong(record.get("Quantity")));
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

}
