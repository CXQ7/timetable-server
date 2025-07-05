package com.lhd.tams.common.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;  
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
    
    private static String secretKey;
    private static long expiration;
    
    @Value("${jwt.secret-key}")
    public void setSecretKey(String secretKey) {
        JwtUtils.secretKey = secretKey;
    }
    
    @Value("${jwt.expiration-time}")
    public void setExpiration(long expiration) {
        JwtUtils.expiration = expiration;
    }

    // 生成Token
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // 解析Token
    public static String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
