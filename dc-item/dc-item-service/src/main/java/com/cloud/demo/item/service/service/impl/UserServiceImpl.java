package com.cloud.demo.item.service.service.impl;

import com.cloud.demo.item.pojo.UserDomain;
import com.cloud.demo.item.pojo.UserRequestParam;
import com.cloud.demo.item.service.dao.UsersDao;
import com.cloud.demo.item.service.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    private final UsersDao usersDao;

    @Autowired
    public UserServiceImpl(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    @Override
    public PageInfo<UserDomain> findUsersBy(int pageNum, int pageSize, UserRequestParam userParam) {
        UserDomain user = new UserDomain();
        if (StringUtils.isNotEmpty(userParam.getNumberParam())){
            user.setNumber(userParam.getNumberParam());
        }
        if (StringUtils.isNotEmpty(userParam.getTelParam())) {
            user.setTel(userParam.getTelParam());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<UserDomain> users = usersDao.select(user);
        return new PageInfo<>(users);
    }
}
