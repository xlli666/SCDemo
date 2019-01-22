package com.cloud.demo.item.api;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/feign")
public interface FeignDemoApi {
    @RequestMapping("/demo")
    List<String> queryList();
}
