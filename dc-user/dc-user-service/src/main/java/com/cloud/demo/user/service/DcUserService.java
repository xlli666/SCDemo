package com.cloud.demo.user.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.cloud.demo.user.service.dao")
public class DcUserService {
    public static void main(String[] args) {
        SpringApplication.run(DcUserService.class, args);
    }
}
