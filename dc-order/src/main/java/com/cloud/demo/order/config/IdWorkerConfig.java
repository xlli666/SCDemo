package com.cloud.demo.order.config;

import com.cloud.demo.common.utils.IdWorker;
import com.cloud.demo.order.property.IdWorkerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(IdWorkerProperties.class)
public class IdWorkerConfig {
    @Bean
    public IdWorker idWorker (IdWorkerProperties prop) {
        return new IdWorker(prop.getWorkerId(), prop.getDataCenterId());
    }
}
