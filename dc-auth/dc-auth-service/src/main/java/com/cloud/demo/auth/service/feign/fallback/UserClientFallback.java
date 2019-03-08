package com.cloud.demo.auth.service.feign.fallback;

import com.cloud.demo.auth.service.feign.UserClient;
import com.cloud.demo.user.pojo.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserClientFallback implements UserClient {
    @Override
    public User queryUser(String username, String password) {
        User user = new User();
        user.setName(username+ UUID.randomUUID().toString());
        return user;
    }
}
