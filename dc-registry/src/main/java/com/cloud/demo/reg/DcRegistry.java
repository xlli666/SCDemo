package com.cloud.demo.reg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DcRegistry {
    public static void main(String[] args) {
        SpringApplication.run(DcRegistry.class, args);
    }
}
