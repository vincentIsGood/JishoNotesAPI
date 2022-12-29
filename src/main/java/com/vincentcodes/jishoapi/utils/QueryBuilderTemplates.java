package com.vincentcodes.jishoapi.utils;

import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public class QueryBuilderTemplates {
    public static QueryBuilder multiSearchPhrase(String phrase, String... fields){
        return QueryBuilders.multiMatchQuery(phrase, fields).type(MultiMatchQueryBuilder.Type.PHRASE);
    }
}
