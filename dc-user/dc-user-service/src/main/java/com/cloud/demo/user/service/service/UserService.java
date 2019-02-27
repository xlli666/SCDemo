package com.cloud.demo.user.service.service;

import com.cloud.demo.user.pojo.User;

public interface UserService {
    Boolean checkData(String data, Integer type);
    Boolean sendVerifyCode(String phone);
    Boolean register(User user, String code);
    User queryUser(String username, String password);
}
