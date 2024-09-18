package com.elasticsearch.demo.service;

import com.elasticsearch.demo.documents.Product;

import java.util.List;

public interface AutoSuggestSearchService {
    List<Product> searchProducts(String partialProductName);
}
