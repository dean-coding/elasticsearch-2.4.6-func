package com.example.es.example;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleSearchRepository extends ElasticsearchRepository<Article, Long> {
}