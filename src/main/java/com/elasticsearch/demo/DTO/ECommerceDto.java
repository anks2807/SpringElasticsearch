package com.elasticsearch.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ECommerceDto {
    private String Country;

    private Long CustomerID;

    private String Description;

    private Date InvoiceDate;

    private String InvoiceNo;

    private Long Quantity;

    private String StockCode;

    private Double UnitPrice;
}
