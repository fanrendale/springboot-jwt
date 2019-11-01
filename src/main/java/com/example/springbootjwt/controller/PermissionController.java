package com.example.springbootjwt.controller;

import com.example.springbootjwt.entity.User;
import com.example.springbootjwt.service.PermissionService;
import com.example.springbootjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Auther: XuJiaFei
 * @Date: 2019/10/31 19:40
 * @Description:
 */
@RestController
public class PermissionController {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;


    @PostMapping("/login")
    public Object login(@RequestBody User user){
        String msg = null;

        if (Objects.nonNull(user) && user.getId() != 0){
            User userFromDb = userService.findUserById(user.getId());

            if (Objects.isNull(userFromDb)){
                msg = "userNotExist";
            }else {
                if (!userFromDb.getPassword().equals(user.getPassword())){
                    msg = "userNotExist";
                }else {
                    Map<String, Object> map = new HashMap<>();
                    String token = permissionService.getToken(userFromDb);
                    map.put("token", token);
                    map.put("user", userFromDb);

                    return map;
                }
            }
        }else {
            msg = "nullMsg";
        }

        return msg;
    }

    @PostMapping("/demos/hello")
    public String hello(){
        return "hello";
    }
}
