package com.shivam.instagram.jwt;

import java.io.IOException;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shivam.instagram.dto.ResponseBody;
import com.shivam.instagram.utils.CookieHandler;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    CookieHandler cookieHandler;

    @Autowired
    AccessTokenJwtUtil accessTokenJwtUtil;

    @Autowired
    RefreshTokenJwtUtil refreshTokenJwtUtil;

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        Optional<Cookie> accessTokenCookie = cookieHandler.getDesiredCookie(request, "access_token");
        Optional<Cookie> refreshTokenCookie = cookieHandler.getDesiredCookie(request, "refresh_token");

        // String token = accessTokenCookie.map(Cookie::getValue).orElse(null);

        if (request.getServletPath().equals("/sign-up") || request.getServletPath().equals("/sign-in")||request.getServletPath().equals("/getAllUser")||request.getServletPath().equals("/createCr7")||request.getServletPath().equals("/delCr7")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            if (accessTokenCookie.isPresent() && refreshTokenCookie.isPresent()) {

                String accessTokenValue = accessTokenCookie.get().getValue();
                String refreshTokenValue = refreshTokenCookie.get().getValue();

                if (accessTokenValue != null &&
                        (SecurityContextHolder.getContext().getAuthentication() == null)) {

                    boolean isAccessTokenExpired = accessTokenJwtUtil.isTokenExpired(accessTokenValue);
                    boolean isRefreshTokenExpired = accessTokenJwtUtil.isTokenExpired(refreshTokenValue);

                    if (isAccessTokenExpired && !isRefreshTokenExpired) {
                        String userKey = refreshTokenJwtUtil.extractUsername(refreshTokenValue);
                        UserDetails userDetails = userDetailsService.loadUserByUsername(userKey);
                        boolean isRefreshTokenValid = refreshTokenJwtUtil.validateToken(refreshTokenValue,
                                userDetails);
                        if (isRefreshTokenValid) {
                            cookieHandler.setAccessTokenInCookie(response, userKey, null, "/",
                                    "None");

                            setSecurityContext(request, userDetails);

                            filterChain.doFilter(request, response);
                            return;

                        }
                    }

                    else if (!isAccessTokenExpired && isRefreshTokenExpired) {
                        String userKey = accessTokenJwtUtil.extractUsername(accessTokenValue);
                        UserDetails userDetails = userDetailsService.loadUserByUsername(userKey);
                        boolean isAccessTokenValid = refreshTokenJwtUtil.validateToken(accessTokenValue,
                                userDetails);
                        if (isAccessTokenValid) {
                            cookieHandler.setRefreshTokenInCookie(response, userKey, null, "/", "None");

                            setSecurityContext(request, userDetails);

                            filterChain.doFilter(request, response);
                            return;

                        }
                    }else {
                        String userKey = accessTokenJwtUtil.extractUsername(accessTokenValue);
                        UserDetails userDetails = userDetailsService.loadUserByUsername(userKey);
                        setSecurityContext(request, userDetails);
                        filterChain.doFilter(request, response);
                        return;
                    }

                }
            }

            else {
                sendJsonResponse(response, false, HttpStatus.FORBIDDEN.value(), null, "JWT Authentication failed",
                    request.getRequestURI(), HttpStatus.FORBIDDEN.value());
            }

            

        } catch (Exception e) {
            accessTokenCookie.ifPresent(auth_cookie -> auth_cookie.setMaxAge(0));
            System.out.println("Exception  ---> "+e);
            sendJsonResponse(response, false, 403, null, "Jwt Authentication Failed", request.getRequestURI(),
                    HttpStatus.FORBIDDEN.value());
            return;

        }

    }

    public void sendJsonResponse(HttpServletResponse response, boolean success, Integer status, Object data,
            String message, String source, Integer responseStatus) throws IOException {

        response.setContentType("application/json");
        response.setStatus(200);

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseBody json = new ResponseBody(success, status, data, message, source);

        String jsonString = objectMapper.writeValueAsString(json);

        response.getWriter().write(jsonString);

    }

    public void setSecurityContext(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        usernamePasswordAuthenticationToken
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

    }

}
