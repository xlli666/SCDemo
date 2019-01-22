package com.cloud.demo.user.service.dao;

import com.cloud.demo.user.pojo.User;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@Component("userDao")
public interface UserDao extends Mapper<User> {
}
