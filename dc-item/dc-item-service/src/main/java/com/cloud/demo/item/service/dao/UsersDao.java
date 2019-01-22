package com.cloud.demo.item.service.dao;

import com.cloud.demo.item.pojo.UserDomain;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@Component("usersDao")
public interface UsersDao extends Mapper<UserDomain> {
}
