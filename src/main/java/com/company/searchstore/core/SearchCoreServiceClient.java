package com.company.searchstore.core;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.SourceConfig;
import co.elastic.clients.elasticsearch.core.search.SourceFilter;
import co.elastic.clients.elasticsearch.core.search.TrackHits;
import com.company.searchstore.dto.Property;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchCoreServiceClient {

    @Value("${es.indexName}")
    private String index;
    @Autowired
    private ElasticsearchClient elasticsearchClient;

    private List<String> excludeFields = List.of("log.file.path", "@version", "@timestamp", "event.original", "message", "host.name");

    public SearchResponse<JsonNode> multiMatch(List<Property> properties, String searchText,
                                     Long offset, Long limit) throws IOException {
        List<String> fields;
        if (properties.get(0) == Property.all) {
            fields = Arrays.stream(Property.values()).map(Enum::name).collect(Collectors.toList());
        } else {
            fields = properties.stream().map(Enum::name).collect(Collectors.toList());
        }
        var multiMatchQ = Query.of(q ->
                q.multiMatch(MultiMatchQuery.of(m -> m.fields(fields)
                        .query(searchText).type(TextQueryType.PhrasePrefix)))
        );

        var boolQ = BoolQuery.of(
                bq -> bq.must(multiMatchQ)
        );

        var request = SearchRequest.of(s -> {
            s.index(index);
            s.size(Math.toIntExact(limit));
            s.from(Math.toIntExact(offset));
            s.query(Query.of(q -> q.bool(boolQ)));
            s.trackTotalHits(TrackHits.of(t -> t.enabled(true)));
            s.source(SourceConfig.of(sc -> sc.filter(SourceFilter.of(sf -> {
                                sf.excludes(excludeFields);
                                sf.includes("*");
                                return sf;
                            }
                    ))
            ));
            return s;
        });
        return elasticsearchClient.search(request, JsonNode.class);
    }

    public SearchResponse<JsonNode> matchPhrasePrefix(Property field, String searchText,
                                            Long offset, Long limit) throws IOException {
        var matchQ = Query.of(q ->
                q.matchPhrasePrefix(MatchPhrasePrefixQuery.of(m -> m.field(field.name()).query(searchText)))
        );

        var boolQ = BoolQuery.of(
                bq ->
                        bq.must(matchQ)
        );

        var request = SearchRequest.of(s -> {
            s.index(index);
            s.size(Math.toIntExact(limit));
            s.from(Math.toIntExact(offset));
            s.query(Query.of(q -> q.bool(boolQ)));
            s.trackTotalHits(TrackHits.of(t -> t.enabled(true)));
            s.source(SourceConfig.of(sc -> sc.filter(SourceFilter.of(sf -> {
                                sf.excludes(excludeFields);
                                sf.includes("*");
                                return sf;
                            }
                    ))
            ));
            return s;
        });


        return elasticsearchClient.search(request, JsonNode.class);
    }

    public SearchResponse<JsonNode> match(Property field, String searchText, Long offset,
                                          Long limit) throws IOException {
        var matchQ = Query.of(q ->
                q.match(MatchQuery.of(m -> m.field(field.name()).query(searchText)))
        );

        var boolQ = BoolQuery.of(
                bq ->
                        bq.must(matchQ)
        );

        var request = SearchRequest.of(s -> {
            s.index(index);
            s.size(Math.toIntExact(limit));
            s.from(Math.toIntExact(offset));
            s.query(Query.of(q -> q.bool(boolQ)));
            s.trackTotalHits(TrackHits.of(t -> t.enabled(true)));
            s.source(SourceConfig.of(sc -> sc.filter(SourceFilter.of(sf -> {
                                sf.excludes(excludeFields);
                                sf.includes("*");
                                return sf;
                            }
                    ))
            ));
            return s;
        });


        return elasticsearchClient.search(request, JsonNode.class);
    }


}
