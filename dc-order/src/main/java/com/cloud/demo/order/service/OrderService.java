package com.cloud.demo.order.service;

import com.cloud.demo.order.pojo.Order;

public interface OrderService {
    long createOrder(Order order);
}
