package com.cloud.demo.auth.util;

import com.cloud.demo.auth.entity.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;

import java.security.PrivateKey;
import java.security.PublicKey;

public class JwtUtil {

    /**
     * 私钥加密token
     *
     * @param userInfo      token中需要携带的对象
     * @param privateKey    私钥
     * @param expireSeconds 过期时间，单位秒
     * @return 生成的token值
     */
    public static String generateToken(UserInfo userInfo, PrivateKey privateKey, int expireSeconds) throws Exception {
        return Jwts.builder()
                .claim(JwtConstant.JWT_KEY_ID, userInfo.getId())
                .claim(JwtConstant.JWT_KEY_USER_NAME, userInfo.getUserName())
                .setExpiration(DateTime.now().plusMinutes(expireSeconds).toDate())
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    /**
     * 私钥加密token
     *
     * @param userInfo      token中需要携带的对象
     * @param privateKey    私钥字节数组
     * @param expireSeconds 过期时间，单位秒
     * @return 生成的token值
     * @throws Exception 异常抛出
     */
    public static String generateToken(UserInfo userInfo, byte[] privateKey, int expireSeconds) throws Exception {
        return Jwts.builder()
                .claim(JwtConstant.JWT_KEY_ID, userInfo.getId())
                .claim(JwtConstant.JWT_KEY_USER_NAME, userInfo.getUserName())
                .setExpiration(DateTime.now().plusMinutes(expireSeconds).toDate())
                .signWith(SignatureAlgorithm.RS256, RsaUtil.getPrivateKey(privateKey))
                .compact();
    }

    /**
     * 公钥解析token
     *
     * @param token     用户请求中的token
     * @param publicKey 公钥
     * @return 解析结果
     */
    private static Jws<Claims> parserToken(String token, PublicKey publicKey) throws Exception {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
    }

    /**
     * 公钥解析token
     *
     * @param token     用户请求中的token
     * @param publicKey 公钥字节数组
     * @return 解析结果
     * @throws Exception 异常抛出
     */
    private static Jws<Claims> parserToken(String token, byte[] publicKey) throws Exception {
        return Jwts.parser().setSigningKey(RsaUtil.getPublicKey(publicKey)).parseClaimsJws(token);
    }

    /**
     * 获取token中的用户信息
     *
     * @param token     用户请求中的令牌
     * @param publicKey 公钥
     * @return 用户信息
     */
    public static UserInfo getInfoFromToken(String token, PublicKey publicKey) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        return new UserInfo(
                ObjectUtil.toLong(body.get(JwtConstant.JWT_KEY_ID)),
                ObjectUtil.toString(body.get(JwtConstant.JWT_KEY_USER_NAME))
        );
    }

    /**
     * 获取token中的用户信息
     *
     * @param token     用户请求中的令牌
     * @param publicKey 公钥字节数组
     * @return 用户信息
     * @throws Exception 异常抛出
     */
    public static UserInfo getInfoFromToken(String token, byte[] publicKey) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        return new UserInfo(
                ObjectUtil.toLong(body.get(JwtConstant.JWT_KEY_ID)),
                ObjectUtil.toString(body.get(JwtConstant.JWT_KEY_USER_NAME))
        );
    }
}
