package com.cloud.demo.order.dao;

import com.cloud.demo.order.pojo.OrderStatus;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@Component
public interface OrderStatusDao extends Mapper<OrderStatus> {
}
