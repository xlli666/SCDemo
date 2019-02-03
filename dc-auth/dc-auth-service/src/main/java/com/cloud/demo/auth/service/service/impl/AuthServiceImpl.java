package com.cloud.demo.auth.service.service.impl;

import com.cloud.demo.auth.entity.UserInfo;
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

    @Autowired
    private JwtProperties jwtProp;

    @Override
    public String authentication(String username, String password) {
        // 查询用户
        User user = new User();
        user.setId(10L);
        user.setUsername(username);
        if (user == null) {
            logger.info("用户信息不存在，{}", username);
            return null;
        }
        // 生成token
        return JwtUtil.generateToken(
                new UserInfo(user.getId(), user.getUsername()),
                jwtProp.getPrivateKey(), jwtProp.getExpire());
    }
}
