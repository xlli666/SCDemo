package com.cloud.demo.auth.service.feign.config;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义HTTP返回状态码未400到500不作熔断降级处理
 * return new HystrixBadRequestException("ErrMsg");暂没起到作用
 */
//@Configuration
public class OverrideFeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        if(response.status() >= 400 && response.status() <= 499){
            return new HystrixBadRequestException("ErrMsg");
        }
        return FeignException.errorStatus(methodKey, response);
    }
}
