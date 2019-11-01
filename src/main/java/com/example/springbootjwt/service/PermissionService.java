package com.example.springbootjwt.service;

import com.example.springbootjwt.entity.User;

/**
 * @Auther: XuJiaFei
 * @Date: 2019/10/31 18:27
 * @Description:
 */
public interface PermissionService {

    /**
     * 获取token
     * @param user
     * @return
     */
    String getToken(User user);

    /**
     * 验证token
     * @param token
     * @return
     */
    User verifyToken(String token) throws Exception;
}
