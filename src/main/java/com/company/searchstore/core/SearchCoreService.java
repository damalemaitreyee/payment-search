package com.company.searchstore.core;

import com.company.searchstore.dto.Operator;
import com.company.searchstore.dto.Property;
import com.company.searchstore.exception.GeneralPaymentsException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.search.MatchQuery;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchCoreService {

    @Value("${es.indexName}")
    private String index;
    @Autowired
    private RestHighLevelClient elasticsearchClient;

    public SearchResponse multiMatch(List<Property> properties, String searchText,
            Long offset, Long limit) {
        List<String> fields;
        if (properties.get(0) == Property.all) {
            fields = Arrays.stream(Property.values()).map(Enum::name).toList();
        } else {
            fields = properties.stream().map(Enum::name).toList();
        }
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.multiMatchQuery(searchText, fields.toArray(new String[0]))
                        .type(MatchQuery.Type.PHRASE_PREFIX));

        return getSearchResponse(offset, limit, boolQueryBuilder);
    }

    public SearchResponse matchPhrasePrefix(Property field, String searchText,
            Long offset, Long limit) {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchPhrasePrefixQuery(field.name(), searchText));

        return getSearchResponse(offset, limit, boolQueryBuilder);
    }

    public SearchResponse match(Property field, String searchText, Long offset,
            Long limit) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery(field.name(), searchText));

        return getSearchResponse(offset, limit, boolQueryBuilder);
    }

    private SearchResponse getSearchResponse(Long offset, Long limit,
            BoolQueryBuilder boolQueryBuilder) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(Math.toIntExact(limit));
        searchSourceBuilder.from(Math.toIntExact(offset));
        searchSourceBuilder.trackTotalHits(true);
        searchSourceBuilder.query(boolQueryBuilder);
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        searchRequest.source(searchSourceBuilder);
        org.elasticsearch.action.search.SearchResponse searchResponse;
        try {
            searchResponse = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new GeneralPaymentsException(e, "Error while querying elastic search");
        }
        return searchResponse;
    }
}
