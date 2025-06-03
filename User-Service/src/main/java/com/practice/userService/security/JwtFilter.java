package com.practice.userService.security;

import com.practice.userService.models.AppUser;
import com.practice.userService.serviceImpl.AppUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AppUserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        System.out.println("JwtFilter - Request URI: " + path);

        if (path.contains("/auth")) {
            System.out.println("JwtFilter - Skipping auth endpoint");
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        System.out.println("JwtFilter - Authorization header: " + authHeader);

        String id = null;
        String jwt = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            id = jwtUtil.extractId(jwt);
            System.out.println("JwtFilter - Extracted id from token: " + id);
        }

        if (id != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            AppUser appUser = userService.loadUserByUsername(id);
            System.out.println("JwtFilter - Loaded user: " + appUser);

            if (jwtUtil.isTokenValid(jwt)) {
                System.out.println("JwtFilter - Token valid, setting auth in context");
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(appUser, null, appUser.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }

}

