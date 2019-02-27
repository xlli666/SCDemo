package com.cloud.demo.auth.service.feign;

import com.cloud.demo.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient(value = "user-service")
@Component
public interface UserClient extends UserApi {
}
