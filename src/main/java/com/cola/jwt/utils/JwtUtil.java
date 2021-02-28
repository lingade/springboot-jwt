package com.cola.jwt.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    /**
     * 过期时间24小时
     */
    private static  long EXPIRE_TIME;
    /**
     * jwt 密钥
     */
    private static String SECRET;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        JwtUtil.SECRET= secret;
    }

    @Value("${jwt.expire}")
    public void setExpireTime(long date) {
        JwtUtil.EXPIRE_TIME= date;
    }

    /**
     * 生成签名，五分钟后过期
     * @param userId
     * @return
     */
    public static String getToken(String userId) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            return JWT.create()
                    // 将 user id 保存到 token 里面
                    .withAudience(userId)

                    .withExpiresAt(date)
                    // token 的密钥
                    .sign(algorithm);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据token获取userId
     * @param token
     * @return
     */
    public static String getUserName(String token) {
        try {
            String userId = JWT.decode(token).getAudience().get(0);
            return userId;
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 校验token
     * @param token
     * @return
     */
    public static DecodedJWT verify(String token){
        JWTVerifier build = JWT.require(Algorithm.HMAC256(SECRET)).build();
        return build.verify(token);
    }

}
