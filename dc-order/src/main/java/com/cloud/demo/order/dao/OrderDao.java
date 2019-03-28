package com.cloud.demo.order.dao;

import com.cloud.demo.order.pojo.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Component
public interface OrderDao extends Mapper<Order> {
    List<Order> queryOrderList(@Param("userId") Long userId, @Param("status") Integer status);
}
