package com.example.springbootjwt.interceptor;

import com.example.springbootjwt.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 自定义拦截器
 *
 * @Auther: XuJiaFei
 * @Date: 2019/10/31 19:23
 * @Description:
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private PermissionService permissionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("token");
        String errMsg = null;

        // 非映射方法直接通过
        if (!(handler instanceof HandlerMethod)){
            System.out.println("非映射方法");
            return true;
        }

        if (Objects.isNull(token)){
            errMsg = "没有token, 请登录";
        }else {
            try {
                permissionService.verifyToken(token);
            } catch (Exception e) {
                errMsg = e.getMessage();
            }
        }

        if (Objects.nonNull(errMsg)){
            System.out.println(errMsg);
            return false;
        }
        return true;
    }
}
