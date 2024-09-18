package com.elasticsearch.demo.service.imple;

import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.elasticsearch.demo.documents.Product;
import com.elasticsearch.demo.service.AutoSuggestSearchService;
import com.elasticsearch.demo.util.ESUtil;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.elastic.clients.elasticsearch.ElasticsearchClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AutoSuggestSearchServiceImpl implements AutoSuggestSearchService {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Override
    public List<Product> searchProducts(String partialProductName) {
        List<Product> products = new ArrayList<>();
        SearchRequest.Builder searchRequest = new SearchRequest.Builder();
        searchRequest.index("products").query(q -> q.match(ESUtil.createAutoSuggestMatchQuery(partialProductName)));
        try {
            SearchResponse<Product> searchResponse = elasticsearchClient.search(searchRequest.build(), Product.class);
            List<Hit<Product>> hits = searchResponse.hits().hits();
            for (Hit<Product> hit: hits) {
                products.add(hit.source());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return products;
    }
}
