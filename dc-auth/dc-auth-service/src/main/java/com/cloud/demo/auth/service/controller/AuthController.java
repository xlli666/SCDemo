package com.cloud.demo.auth.service.controller;

import com.cloud.demo.auth.entity.UserInfo;
import com.cloud.demo.auth.service.property.JwtProperties;
import com.cloud.demo.auth.service.service.AuthService;
import com.cloud.demo.auth.util.JwtUtil;
import com.cloud.demo.common.LayUIData;
import com.cloud.demo.common.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
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

    @RequestMapping("accredit")
    public ResponseEntity<LayUIData> authentication(@RequestParam("username") String username,
                                                    @RequestParam("password") String password,
                                                    HttpServletRequest request, HttpServletResponse response){
        // 登录校验
        String token = authService.authentication(username,password);
        if (StringUtils.isBlank(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LayUIData.failure(HttpStatus.UNAUTHORIZED.value(),HttpStatus.UNAUTHORIZED.getReasonPhrase()));
        }
        // 将token写入cookie,并指定httpOnly为true，防止通过JS获取和修改
        CookieUtils.setCookie(request, response, prop.getCookieName(), token, prop.getCookieMaxAge(),null,true);
        return ResponseEntity.ok().body(LayUIData.formSubResult("OK"));
    }

    @RequestMapping("verify")
    public ResponseEntity<UserInfo> test(@CookieValue("DC_TOKEN") String token,
                                         HttpServletRequest request, HttpServletResponse response){
        try {
            UserInfo userInfo = JwtUtil.getInfoFromToken(token, prop.getPublicKey());
            // 如果成功，还需要刷新token --刷新时机根据时间情况处理
            String newToken = JwtUtil.generateToken(userInfo, prop.getPrivateKey(), prop.getExpire());
            // 将token写入cookie,并指定httpOnly为true，防止通过JS获取和修改
            CookieUtils.setCookie(request, response, prop.getCookieName(), newToken, prop.getCookieMaxAge(), null, true);
            // 成功后返回用户信息
            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            // 抛出异常，token无效，返回错误信息
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}
