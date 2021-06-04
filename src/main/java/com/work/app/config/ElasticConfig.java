package com.work.app.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.work.app.jparepository")
@EnableElasticsearchRepositories(basePackages = "com.work.app.repository")
public class ElasticConfig extends AbstractElasticsearchConfiguration{
  
	@Override
	@Bean
	public RestHighLevelClient elasticsearchClient() {
		final ClientConfiguration clientConfiguration = 
			    ClientConfiguration
			      .builder()
			      .connectedTo("9200")
			      .build();

		return RestClients.create(clientConfiguration).rest();
	}
	
}
