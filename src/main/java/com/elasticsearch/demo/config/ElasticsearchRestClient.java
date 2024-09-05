package com.elasticsearch.demo.config;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchRestClient {
    @Value("${elasticsearch.host}")
    private String host;
    @Value("${elasticsearch.port}")
    private int port;
    @Value("${elasticsearch.connection.request.timeout}")
    private Integer connectionRequestTimeout;

    @Bean
    public RestHighLevelClient elasticsearchClient() {
        RestClientBuilder clientBuilder = RestClient.builder(new HttpHost(host, port, "http"));
        return new RestHighLevelClient(clientBuilder);
    }
}
