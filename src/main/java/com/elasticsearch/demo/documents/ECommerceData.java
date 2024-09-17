package com.elasticsearch.demo.documents;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("ID")
    private Long ID;

    @Field(type = FieldType.Keyword)
    @JsonProperty("Country")
    private String Country;

    @Field(type = FieldType.Long)
    @JsonProperty("CustomerID")
    private Long CustomerID;

    @Field(type = FieldType.Text)
    @JsonProperty("Description")
    private String Description;

    @Field(type = FieldType.Date)
    @JsonProperty("InvoiceDate")
    private String InvoiceDate;

    @Field(type = FieldType.Keyword)
    @JsonProperty("InvoiceNo")
    private String InvoiceNo;

    @Field(type = FieldType.Long)
    @JsonProperty("Quantity")
    private Long Quantity;

    @Field(type = FieldType.Keyword)
    @JsonProperty("StockCode")
    private String StockCode;

    @Field(type = FieldType.Double)
    @JsonProperty("UnitPrice")
    private Double UnitPrice;
}
