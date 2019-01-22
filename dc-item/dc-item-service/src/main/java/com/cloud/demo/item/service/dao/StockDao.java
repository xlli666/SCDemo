package com.cloud.demo.item.service.dao;

import com.cloud.demo.item.pojo.Stock;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@Component("stockDao")
public interface StockDao extends Mapper<Stock> {
}
