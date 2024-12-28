package com.zerobase.domain.config;

import com.zerobase.domain.domain.common.UserType;
import com.zerobase.domain.domain.common.UserVo;
import com.zerobase.domain.util.Aes256Util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtAuthenticationProvider {

    private String secretKey = "scretKey";
    private long tokenValidTime = 1000L * 60 * 60 * 24;

    public String createToken(String userPk, Long id, UserType userType) {
        // Aes256Util.encrypt(userPk) : userPk 암호화
        Claims claims = Jwts.claims().setSubject(Aes256Util.encrypt(userPk))
                                     .setId(Aes256Util.encrypt(id.toString()));
        claims.put("roles", userType); // seller인지 customer인지 결정
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());

        } catch (Exception e) {
            return false;
        }
    }

    public UserVo getUserVo(String token) {
        Claims c = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        // 선생님방식
        // UserVo(Long.valueOf(Objects.requireNonNull(Aes256Util.decrypt(c.getId()))), Aes256Util.decrypt(c.getSubject()))
        return new UserVo(Long.valueOf(Aes256Util.decrypt(c.getId())),
                            Aes256Util.decrypt(c.getSubject()));
    }
}
