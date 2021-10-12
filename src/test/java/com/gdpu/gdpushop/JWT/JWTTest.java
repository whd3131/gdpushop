package com.gdpu.gdpushop.JWT;

import cn.hutool.jwt.JWTUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import org.junit.Test;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author:王浩东
 * @createTime: 2021/10/8
 */
public class JWTTest {

    /*令牌的获取*/
    @Test
    public void test01() {
        HashMap<String,Object> map = new HashMap<>();

        //设置过期时间所需的calendar
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND,50);//20秒后失效

        String token = JWT.create()
                        .withHeader(map)    //Header
                        .withClaim("userId",21) //payLoad
                        .withClaim("username","zs")
                        .withExpiresAt(instance.getTime())    //指定令牌过期时间
                        .sign(Algorithm.HMAC256("!QW#E$R"));

        System.out.println("token = " + token);
    }

    /*令牌的验证*/
    @Test
    public void test02() {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("!QW#E$R")).build();
        DecodedJWT verify = jwtVerifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MzM2ODY4MjcsInVzZXJJZCI6MjEsInVzZXJuYW1lIjoienMifQ.YzAzTBoKmBSvX0d33TEh8zqFT_AssEIM1zGZMFtch5E");
        System.out.println(verify.getClaim("userId"));
        System.out.println(verify.getClaim("username"));

    }

    /*测试hutool里的jwtUtil*/
    @Test
    public void test03() {
        Map<String,Object> map = new HashMap<>();
        map.put("username","zs");
        map.put("password","123456");
        String token = JWTUtil.createToken(map,"1234".getBytes());
        System.out.println("token = " + token);
    }

    /*检验*/
    @Test
    public void test04() {
        boolean res = JWTUtil.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6IjEyMzQ1NiIsInVzZXJuYW1lIjoienMifQ.G4VJug91V0mLcyz3eM4Eew8EzS7xy3ekmobhfbbDa1E",
                "1234".getBytes());
        System.out.println("res = " + res);
    }
}
