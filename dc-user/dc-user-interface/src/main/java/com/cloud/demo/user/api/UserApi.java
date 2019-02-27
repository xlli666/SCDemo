package com.cloud.demo.user.api;

import com.cloud.demo.user.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserApi {
    @RequestMapping("query")
    User queryUser(@RequestParam("username") String username, @RequestParam("password") String password);
}
