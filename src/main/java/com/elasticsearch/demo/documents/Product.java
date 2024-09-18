package com.elasticsearch.demo.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Data
@Document(indexName = "products")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Integer)
    private Integer in_stock;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(type = FieldType.Integer)
    private Integer sold;

    @Field(type = FieldType.Text)
    private String[] tags;

    @Field(type = FieldType.Date, format = DateFormat.date)
    private Date created;


}
