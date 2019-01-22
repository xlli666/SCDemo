package com.cloud.demo.item.service.service;

import com.cloud.demo.item.pojo.UserDomain;
import com.cloud.demo.item.pojo.UserRequestParam;
import com.github.pagehelper.PageInfo;

public interface UserService {
    PageInfo<UserDomain> findUsersBy(int pageNum, int pageSize, UserRequestParam userParam);
}
