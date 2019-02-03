package com.cloud.demo.auth.service.controller;

import com.cloud.demo.auth.service.property.JwtProperties;
import com.cloud.demo.auth.service.service.AuthService;
import com.cloud.demo.common.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class AuthController {

    private final AuthService authService;

    private final JwtProperties prop;

    @Autowired
    public AuthController(AuthService authService, JwtProperties prop) {
        this.authService = authService;
        this.prop = prop;
    }

    @RequestMapping("authTest")
    public ResponseEntity<Void> authentication(@RequestParam("username") String username,
                                               @RequestParam("password") String password,
                                               HttpServletRequest request, HttpServletResponse response){
        // 登录校验
        String token = authService.authentication(username,password);
        if (StringUtils.isBlank(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        System.out.println(password+"---"+token);
        // 将token写入cookie,并指定httpOnly为true，防止通过JS获取和修改
        CookieUtils.setCookie(request, response, prop.getCookieName(), token, prop.getCookieMaxAge(),null,true);
        return ResponseEntity.ok().build();
    }

    @RequestMapping("test")
    public ResponseEntity<Void> test(@RequestParam("username") String username,
                                     @RequestParam("password") String password){
        System.out.println(username+"####"+password);
        return ResponseEntity.ok().build();
    }
}
