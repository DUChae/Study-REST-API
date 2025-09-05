package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // HMAC-SHA 알고리즘을 위한 시크릿 키와 토큰 만료 시간
    private final SecretKey key;
    private final long expirationMs;

    // 생성자에서 시크릿 키와 만료 시간을 주입받아 SecretKey 객체 생성
    public JwtTokenProvider(
            // application.yml에서 값을 읽어옴
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-ms}") long expirationMs
    ) {
        // 시크릿 키를 바이트 배열로 변환
        byte[] keyBytes = (secret.matches("^[A-Za-z0-9+/=]+$") ? Decoders.BASE64.decode(secret) : secret.getBytes());
        // HMAC SHA-256 알고리즘에 맞는 시크릿 키 생성
        this.key = Keys.hmacShaKeyFor(keyBytes);
        //토큰 만료 시간 설정
        this.expirationMs = expirationMs;
    }
    //핵심 : 토큰을 만들고 검증하는 데 필요한 시크릿 키와 만료 시간을 application.yml에서 가져와서 준비하는 과정


    //해당 메서드는 사용자의 정보를 받아 JWT 토큰 생성하는 역할
    public String generateToken(UserDetails user) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);

        return Jwts.builder() //JWT를 만들기 시작하는 빌더
                .setSubject(user.getUsername()) //토큰의 주체(사용자 이름)
                .setIssuedAt(now) //토큰 발행 시간
                .setExpiration(exp) //토큰 만료 시간
                .signWith(key, Jwts.SIG.HS256) // 앞서 만든 시크릿 키로 HS256 알고리즘을 사용하여 토큰에 서명
                .compact(); // 빌더를 통해 만들어진 JWT 를 최종적인 문자열로 압축하여 반환
    }
    //핵심 : 토큰에 사용자 이름, 발행 시간, 만료 시간 등의 정보를 담고, 시크릿 키로 서명하여 안전하게 생성하며 문자열 형태로 변환 = 위변조를 막는 과정


    //해당 메서드는 토큰에서 사용자 이름을 꺼내오는 역할
    public String getUsername(String token) {
        return Jwts.parser() //JWT 파싱(분석) 시작
                .verifyWith(key) //토큰의 서명을 시크릿 키로 검증
                .build() //객체 생성
                .parseSignedClaims(token) //토큰 파싱하고 클레임(정보)을 가져옴
                .getPayload()
                .getSubject();//토큰의 주체 (사용자 이름) 반환
    }
    //핵심 : 토큰이 유효한지 먼저 확인한 다음, 그 안에 담긴 사용자 이름을 추출하는 과정


    //해당 메서드는 토큰의 유효성을 검증하는 역할
    public boolean validate(String token) {
        try {
            //토큰을 파싱하고 서명을 검증
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            //토큰이 잘못되었거나 만료된 경우 예외 발생
            return false;
        }
    }

    public long getExpirationMs() {
        return expirationMs;
    }
}