package com.example.springbootjwt.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.springbootjwt.entity.User;
import com.example.springbootjwt.service.PermissionService;
import com.example.springbootjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Auther: XuJiaFei
 * @Date: 2019/10/31 18:30
 * @Description:
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private UserService userService;

    @Override
    public String getToken(User user) {
        String token;

        token = JWT.create()
                //把用户id存入Token
                .withAudience(String.valueOf(user.getId()))
                //用户密码作为密钥，使用HMAC（消息散列鉴别码）算法加密生成Token
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }

    @Override
    public User verifyToken(String token) throws Exception {
        User user = getUser(token);

        if (Objects.nonNull(user)){
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();

            try {
                jwtVerifier.verify(token);
            } catch (JWTVerificationException e) {
                throw new Exception("invalid token!");
            }
        }else {
            throw new Exception("invalid token!");
        }

        return user;
    }

    private User getUser(String token){
        User user = null;

        String userId = JWT.decode(token).getAudience().get(0);
        user = userService.findUserById(Integer.valueOf(userId));
        user.setId(Integer.valueOf(userId));

        return user;
    }
}
