package com.example.librarysevice.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final String secret = "111111111111111111111111111111111111111111111111111111111111111111111111111111";
    private final Key jwtSecret = Keys.hmacShaKeyFor(secret.getBytes());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        log.info("Received Authorization header: " + token);
        if (token != null && token.startsWith("Bearer ")) {
            try {
                token = token.substring(7);
                log.info("Token after removing 'Bearer ': " + token);
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(jwtSecret)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
                log.info("Parsed claims: " + claims);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        claims.getSubject(), null, new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtException e) {
                log.error("JWT Error: " + e.getMessage());
            }
        } else {
            log.warn("Invalid Authorization header or no Bearer token found");
        }
        filterChain.doFilter(request, response);
    }
}
