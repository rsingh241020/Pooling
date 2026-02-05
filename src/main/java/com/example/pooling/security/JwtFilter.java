package com.example.pooling.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    //    @Override
//    protected void doFilterInternal(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            FilterChain filterChain
//    ) throws ServletException, IOException {
//
//        String path = request.getRequestURI();
//        System.out.println("🔥 JWT FILTER HIT for URI = " + path);
//
//        // ✅ Skip JWT for register & login
//        if (path.contains("/users/register") || path.contains("/users/login")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String authHeader = request.getHeader("Authorization");
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//
//            String token = authHeader.substring(7);
//
//            if (jwtUtil.isTokenValid(token)) {
//
//                Long userId = jwtUtil.extractUserId(token);
//
//
//                UsernamePasswordAuthenticationToken authentication =
//                        new UsernamePasswordAuthenticationToken(
//                                userId,
//                                null,
//                                Collections.emptyList()
//                        );
//
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//
//                request.setAttribute("userId", userId);
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//}
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();
        System.out.println("🔥 JWT FILTER HIT for URI = " + path);

        // ✅ PUBLIC ENDPOINTS SKIP
        if (path.startsWith("/api/users/login") ||
                path.startsWith("/api/users/register")) {

            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);

            if (jwtUtil.isTokenValid(token)) {

                Long userId = jwtUtil.extractUserId(token);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userId,
                                null,
                                Collections.emptyList()
                        );

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);

                request.setAttribute("userId", userId);
            }
        }

        filterChain.doFilter(request, response);
    }
}