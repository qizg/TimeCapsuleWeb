package com.sinwn.capsule.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JWTUtil {
    // 过期时间5分钟
    private static final long EXPIRE_TIME = 5 * 60_1000;

    private static final String USER_ID = "user";

    /**
     * 校验token是否正确
     */
    public static boolean verify(String token, Long userId, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim(USER_ID, userId)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     */
    public static Long getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(USER_ID).asLong();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名
     */
    public static String sign(Long userId, String JWTId, String secret) {
        try {
            long nowTime = System.currentTimeMillis();

            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带username信息
            return JWT.create()
                    .withIssuedAt(new Date(nowTime))
                    .withNotBefore(new Date(nowTime))
                    .withExpiresAt(new Date(nowTime + EXPIRE_TIME))
                    .withJWTId(JWTId)
                    .withClaim(USER_ID, userId)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
