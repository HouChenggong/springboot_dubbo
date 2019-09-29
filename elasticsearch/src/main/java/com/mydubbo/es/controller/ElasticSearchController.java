package com.mydubbo.es.controller;


import com.mydubbo.es.dao.ArticleEsRepository;
import com.mydubbo.es.entity.Article;
import com.mydubbo.es.util.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Api("es")
@RestController
public class ElasticSearchController {
    @Autowired
   private ElasticsearchTemplate elasticsearchTemplate;


    @Autowired
    private ArticleEsRepository articleEsRepository;


    @GetMapping("insert")
    @ApiOperation(value = "insert）", notes = "insert")
    public ResponseResult insert() {

        Article article = new Article();
        article.setAuthor("Alice");
        article.setContent("spring boot data es");
        article.setId(1l);
        article.setPv(100);
        article.setSummary("spring boot es");
        article = articleEsRepository.save(article);
        ResponseResult result = new ResponseResult(true);
        result.setData(article);
        return result;
    }

    @GetMapping("queryForTitle")
    public Optional queryForTitle(String content) {

        Optional article = articleEsRepository.findById((long) 11);


        return article;
    }

}
