package com.elasticsearch.demo.repository;

import com.elasticsearch.demo.documents.ECommerceData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ECommerceRepository extends ElasticsearchRepository<ECommerceData, Long> {
}
