package com.cloud.demo.gw.filter;

import com.cloud.demo.auth.util.JwtUtil;
import com.cloud.demo.common.utils.CookieUtils;
import com.cloud.demo.gw.property.FilterProperties;
import com.cloud.demo.gw.property.JwtProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@EnableConfigurationProperties({JwtProperties.class, FilterProperties.class})
public class LoginFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    private final JwtProperties jwtProp;

    private final FilterProperties filterProp;

    @Autowired
    public LoginFilter(JwtProperties jwtProp, FilterProperties filterProp) {
        this.jwtProp = jwtProp;
        this.filterProp = filterProp;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 5;
    }

    @Override
    public boolean shouldFilter() {
        // 获取上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        // 获取request
        HttpServletRequest request = ctx.getRequest();
        // 获取路径
        String requestURI = request.getRequestURI();
        // 判断白名单
        return !isAllowPath(requestURI);
    }

    @Override
    public Object run() {
        // 获取上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        // 获取request
        HttpServletRequest request = ctx.getRequest();
        // 获取token
        String token = CookieUtils.getCookieValue(request, jwtProp.getCookieName());
        // 校验
        try {
            // 校验通过放行
            JwtUtil.getInfoFromToken(token, jwtProp.getPublicKey());
        } catch (Exception e) {
            // 校验出现异常，返回403
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(403);
            logger.error("非法访问，未登录，地址：{}", request.getRemoteHost(), e );
            //throw new ZuulException(e,403,"非法访问，未登录");
        }
        return null;
    }

    private boolean isAllowPath(String requestURI) {
        // 定义返回标记
        boolean flag = false;
        // 遍历允许访问的路径
        for (String path : filterProp.getAllowPaths()) {
            // 判断是否是符合白名单
            if (requestURI.startsWith(path)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}
