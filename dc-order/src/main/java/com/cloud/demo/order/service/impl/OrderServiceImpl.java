package com.cloud.demo.order.service.impl;

import com.cloud.demo.auth.entity.UserInfo;
import com.cloud.demo.common.utils.IdWorker;
import com.cloud.demo.order.dao.OrderDao;
import com.cloud.demo.order.dao.OrderDetailDao;
import com.cloud.demo.order.dao.OrderStatusDao;
import com.cloud.demo.order.filter.LoginInterceptor;
import com.cloud.demo.order.pojo.Order;
import com.cloud.demo.order.pojo.OrderStatus;
import com.cloud.demo.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderDao orderDao;

    private final OrderDetailDao orderDetailDao;

    private final OrderStatusDao orderStatusDao;

    private final IdWorker idWorker;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, OrderDetailDao orderDetailDao, OrderStatusDao orderStatusDao, IdWorker idWorker) {
        this.orderDao = orderDao;
        this.orderDetailDao = orderDetailDao;
        this.orderStatusDao = orderStatusDao;
        this.idWorker = idWorker;
    }

    @Override
    @Transactional
    public long createOrder(Order order) {
        // 生成orderId
        long orderId = idWorker.nextId();
        // 获取登录用户
        UserInfo user = LoginInterceptor.getLoginUser();
        // 初始化数据
        order.setOrderId(orderId);
        order.setUserId(user.getId());
        // 保存数据
        //orderDao.insertSelective(order);

        // 保存订单状态
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setStatus(1);// 初始状态为未付款
        //orderStatusDao.insertSelective(orderStatus);

        // 订单明细中添加orderId
        order.getOrderDetails().forEach(orderDetail -> orderDetail.setOrderId(orderId));
        // 保存订单明细，使用批量插入功能
        //orderDetailDao.insertList(order.getOrderDetails());

        logger.debug("生成订单，订单编号：{}，用户id：{}", orderId, user.getId());

        return orderId;
    }
}
