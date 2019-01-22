package com.cloud.demo.es.repository;

import com.cloud.demo.es.pojo.ItemESDemo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ItemESDemoRepository extends ElasticsearchRepository<ItemESDemo,Long> {
}
