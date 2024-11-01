package com.shivam.instagram.jwt;

import java.io.IOException;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
    AccessTokenJwtUtil jwtUtil;

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException
            {

        Optional<Cookie> authCookie = cookieHandler.getDesiredCookie(request, "access_token");

        // String token = authCookie.map(Cookie::getValue).orElse(null);

        if(request.getServletPath().equals("/sign-up"))
        {
            filterChain.doFilter(request, response);
            return ;
        }


        try {
            if(authCookie.isPresent()) {
                String token = authCookie.get().getValue();
                String email = jwtUtil.extractEmail(token);
                String userName = jwtUtil.extractUsername(token);

                if (token != null && userName != null && email != null &&
                        !jwtUtil.isTokenExpired(token) &&
                        (SecurityContextHolder.getContext().getAuthentication() == null)) {

                    UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

                    boolean isJwtValid = jwtUtil.validateToken(token, userDetails);

                    if (isJwtValid) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                        usernamePasswordAuthenticationToken
                                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                        filterChain.doFilter(request, response);
                        return ; 
                    }

                }

            } else{
                filterChain.doFilter(request, response);
                return;
            }

            sendJsonResponse(response, false, HttpStatus.FORBIDDEN.value(), null, "JWT Authentication failed", request.getRequestURI(),HttpStatus.FORBIDDEN.value());

        } catch (Exception e) {
            authCookie.ifPresent(auth_cookie -> auth_cookie.setMaxAge(0));
            sendJsonResponse(response, false, 403, null, "Jwt Authentication Failed", request.getRequestURI(),HttpStatus.FORBIDDEN.value());
            return;

        }


    }





    public void sendJsonResponse(HttpServletResponse response , boolean success ,Integer status, Object data ,String message, String source,Integer responseStatus ) throws IOException
    {

    
        response.setContentType("application/json");
        response.setStatus(200);
        
        ObjectMapper objectMapper = new ObjectMapper();

        ResponseBody json = new ResponseBody(success, status, data, message, source);

        String jsonString = objectMapper.writeValueAsString(json);

        response.getWriter().write(jsonString);

    }

}
