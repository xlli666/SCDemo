package com.cloud.demo.order.controller;

import com.cloud.demo.order.pojo.Order;
import com.cloud.demo.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
@Api("订单服务接口")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /** 测试 */
    @PostMapping("order")
    @ApiOperation(value = "测试接口，返回字符串", notes = "接口测试")
    @ApiImplicitParam(name = "str", value = "测字符串，任意输入")
    public ResponseEntity<String> mApiDemo(String str){
        System.out.println(str);
        return new ResponseEntity<>("hello: " + str, HttpStatus.CREATED);
    }

    @PostMapping("")
    @ApiOperation(value = "测试接口，返回字符串", notes = "接口测试")
    @ApiImplicitParam(name = "str2", value = "测字符串，任意输入")
    public ResponseEntity<String> mApiDemo2(String str2){
        System.out.println(str2);
        return new ResponseEntity<>("hello2: " + str2, HttpStatus.CREATED);
    }
    /** 测试-end */

    @PostMapping("create")
    @ApiOperation(value = "创建订单接口，返回订单编号", notes = "创建订单")
    @ApiImplicitParam(name = "order", required = true, value = "订单的json对象,包含订单明细和物流信息")
    public ResponseEntity<Long> createOrder(@RequestBody @Valid Order order){
        Long id = orderService.createOrder(order);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }
}
