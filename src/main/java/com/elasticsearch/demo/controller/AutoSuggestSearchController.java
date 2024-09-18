package com.elasticsearch.demo.controller;

import com.elasticsearch.demo.documents.Product;
import com.elasticsearch.demo.service.AutoSuggestSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AutoSuggestSearchController {

    @Autowired
    private AutoSuggestSearchService autoSuggestSearchService;

    @GetMapping("/search/{partialProductName}/products")
    public List<Product> searchProducts(@PathVariable String partialProductName) {
        return autoSuggestSearchService.searchProducts(partialProductName);
    }

}
