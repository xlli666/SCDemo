package com.cloud.demo.item.service.dao;

import com.cloud.demo.item.pojo.Sku;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@Component("skuDao")
public interface SkuDao extends Mapper<Sku> {
}
