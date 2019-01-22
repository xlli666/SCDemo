package com.cloud.demo.item.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.cloud.demo.item.service.dao")
public class DcItemService {
    public static void main(String[] args) {
        SpringApplication.run(DcItemService.class, args);
    }
}
