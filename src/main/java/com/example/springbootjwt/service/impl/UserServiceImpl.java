package com.example.springbootjwt.service.impl;

import com.example.springbootjwt.entity.User;
import com.example.springbootjwt.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: XuJiaFei
 * @Date: 2019/10/31 18:12
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {
    /**
     * 默认用户数据
     */
    static List<User> userList = Arrays.asList(
            new User(1,"xjf", "123456"),
            new User(2,"dale", "123456"),
            new User(3,"fanren", "123456"),
            new User(4,"xiaoming", "123456"),
            new User(5,"zhangsan", "123456")
    );


    @Override
    public User findUserById(int id) {
        User tempUser = new User();
        userList.forEach(user -> {
          if (user.getId() == id){
              BeanUtils.copyProperties(user,tempUser);
          }
        });
        return tempUser;
    }
}
