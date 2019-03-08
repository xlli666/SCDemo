package com.cloud.demo.auth.service.feign;

import com.cloud.demo.auth.service.feign.config.FeignConfig;
import com.cloud.demo.auth.service.feign.fallback.UserClientFallback;
import com.cloud.demo.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

// 也可以使用fallbackFactory = UserClientFallbackFactory.class; 404不作降级处理时设置decode404 = true
@FeignClient(value = "user-service", fallback = UserClientFallback.class, configuration = FeignConfig.class)
@Component
public interface UserClient extends UserApi {
}
