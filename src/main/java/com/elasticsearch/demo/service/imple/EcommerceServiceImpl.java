package com.elasticsearch.demo.service.imple;

import com.elasticsearch.demo.DTO.ECommerceDto;
import com.elasticsearch.demo.DTO.ReturnMessageDto;
import com.elasticsearch.demo.documents.ECommerceData;
import com.elasticsearch.demo.repository.ECommerceRepository;
import com.elasticsearch.demo.service.EcommerceService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class EcommerceServiceImpl implements EcommerceService {

    @Autowired
    ECommerceRepository eCommerceRepository;

    @Override
    public ReturnMessageDto ingestBulkData() {
        List<ECommerceDto> eCommerceDtos = readCSV();
        for(ECommerceDto eCommerceDto : eCommerceDtos) {
            eCommerceRepository.save(toEcommerceDocument(eCommerceDto));
        }
        return new ReturnMessageDto("Data ingested successfully", 200);
    }

    private static List<ECommerceDto> readCSV() {
        String path = "C:\\Users\\ankit\\Downloads\\archive_1\\data.csv";
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy H:m");
        List<ECommerceDto> eCommerceDtos = null;
        try (
                BufferedReader br = new BufferedReader(new FileReader(path));
                CSVParser parser = CSVFormat.DEFAULT.withDelimiter(',').withHeader().parse(br);
        ) {
            eCommerceDtos = new ArrayList<>();
            for (CSVRecord record : parser) {
                ECommerceDto eCommerceDto = new ECommerceDto();
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
            System.out.println(e);
        }
        return eCommerceDtos;
    }

    private ECommerceData toEcommerceDocument(ECommerceDto eCommerceDto) {
        ECommerceData eCommerceData = new ECommerceData();
        eCommerceData.setCountry(eCommerceDto.getCountry());
        eCommerceData.setQuantity(eCommerceDto.getQuantity());
        eCommerceData.setDescription(eCommerceDto.getDescription());
        eCommerceData.setInvoiceDate(eCommerceDto.getInvoiceDate());
        eCommerceData.setInvoiceNo(eCommerceDto.getInvoiceNo());
        eCommerceData.setCustomerID(eCommerceDto.getCustomerID());
        eCommerceData.setStockCode(eCommerceDto.getStockCode());
        eCommerceData.setUnitPrice(eCommerceDto.getUnitPrice());
        return eCommerceData;
    }

}
