package com.practice.todo.security;

import com.practice.todo.dto.UserDTO;
import com.practice.todo.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@AllArgsConstructor
public class JwtUtil {

    private final String SECRET = "uU3GcBQeATjUkPn6J+0GEm6boN9d93fPPTgGB5b74G0=";
    private final Key SIGNING_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    private final UserService userService;

    public boolean isTokenValid(String token) {
        try {
            String userId = extractId(token);
            if (isValidUser(userId) && !isTokenExpired(token)) {
                return true;
            }
        } catch (JwtException e) {
            return false;
        }
        return false;
    }

    public String extractId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SIGNING_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId", String.class);
    }

    public boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SIGNING_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (JwtException e) {
            return true;
        }
    }

    public boolean isValidUser(String userId) {
        try {
            UserDTO user = userService.getUserById(userId);
            return user != null;
        } catch (JwtException e) {
            return false;
        }
    }

}
