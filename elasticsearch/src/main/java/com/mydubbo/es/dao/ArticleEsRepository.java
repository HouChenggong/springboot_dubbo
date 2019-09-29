package com.mydubbo.es.dao;


import com.mydubbo.es.entity.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * ArticleEsRepository class
 * 实现Article在es中的crud、分页、排序、高亮关键字
 *
 */
@Repository
public interface ArticleEsRepository extends CrudRepository<Article,Long> {

}