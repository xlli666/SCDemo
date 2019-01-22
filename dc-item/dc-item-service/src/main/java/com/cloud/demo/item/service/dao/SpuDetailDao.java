package com.cloud.demo.item.service.dao;

import com.cloud.demo.item.pojo.SpuDetail;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@Component("spuDetailDao")
public interface SpuDetailDao extends Mapper<SpuDetail> {
}
