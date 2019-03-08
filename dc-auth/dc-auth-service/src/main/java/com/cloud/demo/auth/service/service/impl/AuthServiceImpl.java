package com.cloud.demo.auth.service.service.impl;

import com.cloud.demo.auth.entity.UserInfo;
import com.cloud.demo.auth.service.feign.UserClient;
import com.cloud.demo.auth.service.property.JwtProperties;
import com.cloud.demo.auth.service.service.AuthService;
import com.cloud.demo.auth.util.JwtUtil;
import com.cloud.demo.user.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("authService")
public class AuthServiceImpl implements AuthService {

    private static Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final JwtProperties jwtProp;

    private final UserClient userClient;

    @Autowired
    public AuthServiceImpl(JwtProperties jwtProp, UserClient userClient) {
        this.jwtProp = jwtProp;
        this.userClient = userClient;
    }

    @Override
    public String authentication(String username, String password) {
        try {
            // 查询用户
            User user = userClient.queryUser(username, password);
            if (null == user) {
                logger.info("用户信息不存在，{}", username);
                return null;
            }
            if (!user.getName().equals(username)) {
                logger.info("服务异常");
                return null;
            }
            // 生成token
            return JwtUtil.generateToken(
                    new UserInfo(user.getId(), user.getName()),
                    jwtProp.getPrivateKey(), jwtProp.getExpire());
        } catch (Exception e) {
            return null;
        }
    }
}
