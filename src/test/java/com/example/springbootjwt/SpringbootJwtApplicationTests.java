package com.example.springbootjwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Springboot使用JWT测试
 */
@SpringBootTest
class SpringbootJwtApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     * 1.生成不携带自定义信息的JWT token
     */
    @Test
    void test1(){
        //1. 构建头部信息
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        //2. 构建秘钥信息
        Algorithm algorithm = Algorithm.HMAC256("secret");

        //3. 我们通过定义注册和自定义声明  并组合头部信息和秘钥信息生成jwt token

        //当前时间
        Date nowDate = new Date();
        Instant instant = nowDate.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
        //当前时间加2小时，作为过期时间
        LocalDateTime expire = localDateTime.plusHours(2);
        ZonedDateTime zonedDateTime = expire.atZone(zoneId);
        //过期时间
        Date expireDate = Date.from(zonedDateTime.toInstant());

        String token = JWT.create()
                //设置头部信息Header
                .withHeader(map)
                //设置载荷 签名是由生成 例如 服务器
                .withIssuer("SERVICE")
                //设置载荷 签名的主题
                .withSubject("this is test token")
                //设置载荷  定义在什么时间之前，该jwt都是不可用的
//                .withNotBefore(new Date())
                //设置载荷 签名的观众  也可以理解谁接收签名的
                .withAudience("APP")
                //设置载荷 生成签名的时间
                .withIssuedAt(nowDate)
                //设置载荷 签名过期的时间
                .withExpiresAt(expireDate)
                //签名
                .sign(algorithm);

        System.out.println(token);
    }

    /**
     * 2. 生成携带自定义信息 JWT token
     */
    @Test
    String test2(){
        //1. 构建头部信息
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        //2. 构建秘钥信息
        Algorithm algorithm = Algorithm.HMAC256("secret");

        //3. 我们通过定义注册和自定义声明  并组合头部信息和秘钥信息生成jwt token

        //当前时间
        Date nowDate = new Date();
        Instant instant = nowDate.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
        //当前时间加2小时，作为过期时间
        LocalDateTime expire = localDateTime.plusHours(2);
        ZonedDateTime zonedDateTime = expire.atZone(zoneId);
        //过期时间
        Date expireDate = Date.from(zonedDateTime.toInstant());

        String token = JWT.create()
                .withHeader(map)
                .withClaim("loginName", "zhangsan")
                .withClaim("userName", "张三")
                .withClaim("deptName", "技术部")
                .withIssuer("SERVICE")
                .withSubject("this is a test token")
                .withAudience("APP")
                .withIssuedAt(nowDate)
                .withExpiresAt(expireDate)
                .sign(algorithm);

        System.out.println(token);

        return token;
    }

    /**
     * 验证JWT Token
     */
    @Test
    void test3(){
        String token = test2();

        //1. 构建头部信息
        Algorithm algorithm = Algorithm.HMAC256("secret");

        //2. 通过秘钥信息和签名的发布者的信息生成JWTVerifier（JWT验证类）
        // 不添加.withIssuer("SERVICE")也可以获取JWTVerifier
        JWTVerifier verifier = JWT.require(algorithm)
                                .withIssuer("SERVICE")
                                .build();

        //3. 通过JWTVerifier的verify获取token中的信息
        DecodedJWT jwt = verifier.verify(token);

        //4. 获取信息
        String subject = jwt.getSubject();
        List<String> audience = jwt.getAudience();
        Map<String, Claim> claims = jwt.getClaims();
        claims.forEach((key,value) -> System.out.println("key= " + key + "    value=" + value.asString()));

        Claim loginName = claims.get("loginName");
        System.out.println(loginName.asString());
        System.out.println(subject);
        System.out.println(audience.get(0));



    }
}
