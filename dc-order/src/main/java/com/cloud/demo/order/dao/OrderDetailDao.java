package com.cloud.demo.order.dao;

import com.cloud.demo.order.pojo.OrderDetail;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

@Component
public interface OrderDetailDao extends Mapper<OrderDetail>, InsertListMapper<OrderDetail> {
}
