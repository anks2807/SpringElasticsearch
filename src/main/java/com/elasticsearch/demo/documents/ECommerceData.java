package com.elasticsearch.demo.documents;

import lombok.Data;

import java.util.Date;

@Data
public class ECommerceData {

    private Long id;

    private String Country;

    private Long CustomerID;

    private String Description;

    private Date InvoiceDate;

    private String InvoiceNo;

    private Long Quantity;

    private String StockCode;

    private Double UnitPrice;
}
