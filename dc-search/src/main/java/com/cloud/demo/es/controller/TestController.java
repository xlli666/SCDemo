package com.cloud.demo.es.controller;

import com.cloud.demo.es.pojo.ItemESDemo;
import com.cloud.demo.es.repository.ItemESDemoRepository;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class TestController {

    private final ItemESDemoRepository repository;

    @Autowired
    public TestController(ItemESDemoRepository repository) {
        this.repository = repository;
    }

    @RequestMapping("/search")
    public ResponseEntity<String> search(){
        // 1、创建查询构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 2、查询
        // 2.1、对结果进行筛选 查询的列 _source:[id,skus]
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id","skus"},null));
        // 2.2、基本查询 查询的列满足的条件 query:{match:{all:keyword}}
        queryBuilder.withQuery(QueryBuilders.matchQuery("all","keyword"));
        // 2.3、分页User
        queryBuilder.withPageable(PageRequest.of(1,10));
        // 2.4、排序
        String sortBy = "";
        Boolean desc = false;
        if (StringUtils.isNotBlank(sortBy)){
            queryBuilder.withSort(SortBuilders.fieldSort(sortBy).order(desc ? SortOrder.DESC : SortOrder.ASC));
        }
        // 3、返回结果
        Page<ItemESDemo> result = repository.search(queryBuilder.build());
        // 4、解析结果
        result.getContent();
        result.getTotalElements();
        result.getTotalPages();

        return ResponseEntity.ok("OK");
    }
}
