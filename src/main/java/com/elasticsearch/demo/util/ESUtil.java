package com.elasticsearch.demo.util;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;

import java.util.function.Supplier;

public class ESUtil {

    public static Supplier<Query> createAutoSuggestSupplier(String partialProductName) {
        return () -> Query.of(q -> q.match(createAutoSuggestMatchQuery(partialProductName)));
    }

    public static MatchQuery createAutoSuggestMatchQuery(String partialProductName) {

        MatchQuery.Builder matchQueryBuilder = new MatchQuery.Builder();
        return matchQueryBuilder.field("name").query(partialProductName).analyzer("standard").build();

    }
}
