package com.elasticsearch.demo.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Data
@Document(indexName = "ecommerce_data")
public class ECommerceData {

    @Id
    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Keyword)
    private String Country;

    @Field(type = FieldType.Long)
    private Long CustomerID;

    @Field(type = FieldType.Text)
    private String Description;

    @Field(type = FieldType.Date)
    private Date InvoiceDate;

    @Field(type = FieldType.Keyword)
    private String InvoiceNo;

    @Field(type = FieldType.Long)
    private Long Quantity;

    @Field(type = FieldType.Keyword)
    private String StockCode;

    @Field(type = FieldType.Double)
    private Double UnitPrice;
}
