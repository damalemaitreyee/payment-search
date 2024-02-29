package com.company.searchstore.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import org.elasticsearch.action.search.SearchResponse;
import com.company.searchstore.core.SearchCoreService;
import com.company.searchstore.dto.Operator;
import com.company.searchstore.dto.PaymentSearchRequest;
import com.company.searchstore.dto.Property;
import com.company.searchstore.dto.PaymentSearchResponse;
import com.company.searchstore.exception.GeneralPaymentsException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {

    @Autowired
    SearchCoreService searchCoreService;

    ObjectMapper objectMapper = new ObjectMapper();

    public PaymentSearchResponse searchPayments(PaymentSearchRequest searchRequest, Long limit, Long offset) {
        Operator operator = searchRequest.getOperator();
        List<Property> properties = searchRequest.getProperty();
        String searchText = searchRequest.getSearchText();
        SearchResponse response = null;

        if (properties.get(0).equals("all")) {
            if (operator != Operator.multi_match) {
                throw new GeneralPaymentsException(
                        "If you are searching in all search fields, then you have to pass multi_match as a operator");
            }
        }

        switch (operator) {
            case match:
                response = searchCoreService.match(properties.get(0), searchText, offset, limit);
                break;
            case match_phrase_prefix:
                response = searchCoreService.matchPhrasePrefix(properties.get(0), searchText, offset, limit);
                break;
            case multi_match:
                response = searchCoreService.multiMatch(properties, searchText, offset, limit);
                break;
            default:
                throw new GeneralPaymentsException("Operator is not supported" + operator);
        }

        return mapPaymentResponse(limit, offset, response);
    }

    private PaymentSearchResponse mapPaymentResponse(Long limit, Long offset, SearchResponse response) {
        SearchHit[] searchHit = response.getHits().getHits();

        List<JsonNode> generalPayments = new ArrayList<>();

        for (SearchHit hit : searchHit) {
            JsonNode payment = objectMapper.convertValue(hit.getSourceAsMap(), JsonNode.class);
            generalPayments.add(payment);
        }

        return PaymentSearchResponse.builder()
                .payments(generalPayments)
                .limit(limit)
                .offset(offset)
                .totalCount(response.getHits().getTotalHits().value)
                .build();
    }
}
