package com.company.searchstore.services;

import co.elastic.clients.elasticsearch.core.search.Hit;
import com.company.searchstore.core.SearchCoreServiceClient;
import com.company.searchstore.dto.Operator;
import com.company.searchstore.dto.PaymentSearchRequest;
import com.company.searchstore.dto.PaymentSearchResponse;
import com.company.searchstore.dto.Property;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {

    @Autowired
    SearchCoreServiceClient searchCoreServiceClient;

    ObjectMapper objectMapper = new ObjectMapper();

    public PaymentSearchResponse searchPayments(PaymentSearchRequest searchRequest, Long limit, Long offset)
            throws IOException {
        Operator operator = searchRequest.getOperator();
        List<Property> properties = searchRequest.getProperty();
        String searchText = searchRequest.getSearchText();
        co.elastic.clients.elasticsearch.core.SearchResponse<JsonNode> response = null;

        if (properties.get(0) == Property.all) {
            if (operator != Operator.multi_match) {
                throw new IllegalArgumentException(
                        "If you are searching in all search fields, then you have to pass multi_match as a operator");
            }
        }
        switch (operator){
            case match:
                response =  searchCoreServiceClient.match(properties.get(0), searchText, offset, limit);
                break;
            case match_phrase_prefix:
                response =  searchCoreServiceClient.matchPhrasePrefix(properties.get(0), searchText, offset, limit);
                break;
            case multi_match:
                response =  searchCoreServiceClient.multiMatch(properties, searchText, offset, limit);
                break;
        }

        return mapPaymentResponse(limit, offset, response);
    }

    private PaymentSearchResponse mapPaymentResponse(Long limit, Long offset, co.elastic.clients.elasticsearch.core.SearchResponse<JsonNode> response) {
        List<Hit<JsonNode>> hits = response.hits().hits();

        List<JsonNode> generalPayments = new ArrayList<>();

        for (Hit<JsonNode> hit: hits) {
            JsonNode row = hit.source();
           generalPayments.add(row);
        }

        return PaymentSearchResponse.builder()
                .payments(generalPayments)
                .limit(limit)
                .offset(offset)
                .totalCount(response.hits().total().value())
                .build();
    }

}
