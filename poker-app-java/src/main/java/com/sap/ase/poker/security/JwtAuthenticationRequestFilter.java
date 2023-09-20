package com.sap.ase.poker.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

public class JwtAuthenticationRequestFilter extends BasicAuthenticationFilter {

    private final JwtTools jwtTools;

    public JwtAuthenticationRequestFilter(AuthenticationManager authenticationManager, JwtTools jwtTools) {
        super(authenticationManager);
        this.jwtTools = jwtTools;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (!cookie.getName().equalsIgnoreCase("jwt")) {
                    continue;
                }
                try {
                    String cookieValue = URLDecoder.decode(cookie.getValue(), "UTF-8");
                    DecodedJWT decodedJwt = jwtTools.verifyAndDecode(cookieValue);
                    String userId = decodedJwt.getClaim("user_id").asString();
                    String userName = decodedJwt.getClaim("user_name").asString();

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } catch (JWTVerificationException ignored) {
                }
            }
        }

        chain.doFilter(request, response);
    }
}