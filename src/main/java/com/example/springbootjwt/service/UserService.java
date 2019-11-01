package com.example.springbootjwt.service;

import com.example.springbootjwt.entity.User;

/**
 * @Auther: XuJiaFei
 * @Date: 2019/10/31 18:11
 * @Description:
 */
public interface UserService {

    User findUserById(int id);
}
