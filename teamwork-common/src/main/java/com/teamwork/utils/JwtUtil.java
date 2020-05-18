package com.teamwork.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.teamwork.consts.ShiroConsts;

import java.util.Date;

public class JwtUtil {
//    @Autowired
//    JwtProperties jwtProperties;
//
//    @Autowired
//    private static JwtUtil jwtUtil;

//    @PostConstruct
//    public void init() {
//        jwtUtil = this;
//        jwtUtil.jwtProperties = this.jwtProperties;
//    }

    /**
     * 校验token是否正确
     * @param token
     * @return
     */
    public static boolean verify(String token) {
        String secret = getClaim(token, ShiroConsts.ACCOUNT) + "teamwork";
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        verifier.verify(token);
        return true;
    }

    /**
     * 获得Token中的信息无需secret解密也能获得
     * @param token
     * @param claim
     * @return
     */
    public static String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,n分钟后过期
     * @param account
     * @param currentTimeMillis
     * @return
     */
    public static String sign(String account, String currentTimeMillis) {
        // 帐号加JWT私钥加密
        String secret = account + "teamwork";
        // 此处过期时间，单位：毫秒
        Date date = new Date(System.currentTimeMillis() + 3 * 60 * 1000L);
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withClaim(ShiroConsts.ACCOUNT, account)
                .withClaim("currentTimeMillis", currentTimeMillis)
                .withExpiresAt(date)
                .sign(algorithm);
    }
}
