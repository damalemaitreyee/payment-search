package com.company.searchstore.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class EcsConfig {

    @Bean
    public ElasticsearchClient getEcsClient(@Value("${es.url}") String ecsHost, @Value("${es.user}") String user,
            @Value("${es.password}") String password) {
        final CredentialsProvider credentialsProvider =
                new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(user, password));

        RestClient restClient = RestClient.builder(HttpHost.create(ecsHost)).setHttpClientConfigCallback(
                new HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(
                            HttpAsyncClientBuilder httpClientBuilder) {
                        return httpClientBuilder
                                .setDefaultCredentialsProvider(credentialsProvider);
                    }
                }).build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }

    @Bean
    public RestHighLevelClient elasticsearchClient(@Value("${es.url}") String ecsHost, @Value("${es.user}") String user,
            @Value("${es.password}") String password) {
        RestHighLevelClient restHighLevelClient = null;
        try {

            final CredentialsProvider credentialsProvider =
                    new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(user, password));

            restHighLevelClient = new RestHighLevelClient(
                    RestClient.builder(
                                    new HttpHost(HttpHost.create(ecsHost)))
                            .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                                @Override
                                public HttpAsyncClientBuilder customizeHttpClient(
                                        HttpAsyncClientBuilder httpClientBuilder) {
                                    return httpClientBuilder
                                            .setDefaultCredentialsProvider(credentialsProvider);
                                }
                            }));

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return restHighLevelClient;
    }
}