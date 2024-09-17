package com.elasticsearch.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientAutoConfiguration.class
})
public class SpringBootElasticApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootElasticApplication.class, args);
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}



}
