package com.rostami.onlinehomeservices.util;

import com.rostami.onlinehomeservices.dto.api.auth.in.JwtRequestParam;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import static java.lang.String.format;

@Component
@Slf4j
public class JwtTokenUtil implements Serializable {
    @Serial
    private static final long serialVersionUID = -2550185165626007488L;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.issuer}")
    private String issuer;

    public String generateAccessToken(JwtRequestParam param) {

        Date now = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        Date oneWeekFromNow = Date.from(LocalDateTime.now().plusWeeks(1).toInstant(ZoneOffset.UTC));

        return Jwts.builder()
                .setSubject(format("%s,%s",param.getEmail(),  param.getPassword()))
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(oneWeekFromNow) // 1 week
                .signWith(SignatureAlgorithm.HS512, generalKey())
                .compact();
    }

    public byte[] generalKey() {
        return secret.getBytes(StandardCharsets.UTF_8);
    }

    public String getEmail(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(generalKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(",")[0];
    }

    public boolean validate(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(generalKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }
}
