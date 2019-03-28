package com.cloud.demo.order.config;

import com.cloud.demo.order.filter.LoginInterceptor;
import com.cloud.demo.order.property.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class MvcConfig implements WebMvcConfigurer {

    private final JwtProperties jwtProp;

    @Autowired
    public MvcConfig(JwtProperties jwtProp) {
        this.jwtProp = jwtProp;
    }

    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor(jwtProp);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**");
    }
}
