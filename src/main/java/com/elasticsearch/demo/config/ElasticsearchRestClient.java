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
    private String hostName;

    @Value("${elasticsearch.port}")
    private int port;



    @Bean
    public RestHighLevelClient elasticsearchClient() {
        RestClientBuilder clientBuilder = RestClient.builder(new HttpHost(hostName, port, "http"))
                .setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
                    @Override
                    public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                        return requestConfigBuilder
                                .setConnectionRequestTimeout(60000);
                    }
                });
        return new RestHighLevelClient(clientBuilder);
    }


}
