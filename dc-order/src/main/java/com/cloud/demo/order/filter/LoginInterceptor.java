package com.cloud.demo.order.filter;

import com.cloud.demo.auth.entity.UserInfo;
import com.cloud.demo.auth.util.JwtUtil;
import com.cloud.demo.common.utils.CookieUtils;
import com.cloud.demo.order.property.JwtProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    private JwtProperties jwtProp;
    // 定义一个线程域，存放登录用户
    private static final ThreadLocal<UserInfo> tl = new ThreadLocal<>();
    public LoginInterceptor(JwtProperties jwtProp){
        this.jwtProp = jwtProp;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 查询token
        String token = CookieUtils.getCookieValue(request, jwtProp.getCookieName());
        if (StringUtils.isBlank(token)) {
            // 未登录,返回401
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        // 有token，查询用户信息
        try {
            // 解析成功，证明已经登录
            UserInfo user = JwtUtil.getInfoFromToken(token, jwtProp.getPublicKey());
            // 放入线程域
            tl.set(user);
            return true;
        } catch (Exception e) {
            // 抛出异常，证明未登录或超时,返回401
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        tl.remove();
    }

    public static UserInfo getLoginUser(){
        return tl.get();
    }
}
