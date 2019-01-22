package com.cloud.demo.item.service.dao;

import com.cloud.demo.item.pojo.Spu;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@Component("spuDao")
public interface SpuDao extends Mapper<Spu> {
}
