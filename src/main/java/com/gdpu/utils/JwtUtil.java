package com.gdpu.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.Map;

/**
 * Jwt工具类，生成JWT和认证
 * @author:王浩东
 * @createTime: 2021/10/9
 */
@Slf4j
public class JwtUtil {

    //秘钥
    private static final String SIGN = "!ZUCK!!";

    /**
     * 生成token
     */
    public static String getToken(Map<String,String> map){
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE,1); //1天过期

        //创建jwt builder
        JWTCreator.Builder builder = JWT.create();

        //payLoad
        map.forEach((k,v)->{
            builder.withClaim(k,v); //把要从后端要传的值都放进去token
        });

        String token = builder.withExpiresAt(instance.getTime()) //指定令牌过期时间
            .sign(Algorithm.HMAC256(SIGN)); //SIGN签名

        return token;
    }

    /**
     * 验证token
     * return: DecodedJWT (如果验证存在问题，是无法返回这个对象的）
     */
    public static DecodedJWT verify(String token){
        //创建验证对象,如果验证存在任何问题，这句都会抛出异常
        DecodedJWT verifier = JWT.require(Algorithm.HMAC256(SIGN))
                .build()
                .verify(token);
        return verifier;
    }



}

