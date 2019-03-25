package com.cloud.demo.order.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
@Api("订单服务接口")
public class OrderController {

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
}
