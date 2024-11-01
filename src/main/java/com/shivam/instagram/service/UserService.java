package com.shivam.instagram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.shivam.instagram.controller.UserWrapper;
import com.shivam.instagram.dto.ResponseBody;
import com.shivam.instagram.entity.User;
import com.shivam.instagram.jwt.AccessTokenJwtUtil;
import com.shivam.instagram.jwt.RefreshTokenJwtUtil;
import com.shivam.instagram.repository.UserRepository;
import com.shivam.instagram.utils.CookieHandler;
import com.shivam.instagram.utils.Time;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Time time;

    @Autowired
    CustomUserDetailService userDetailService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AccessTokenJwtUtil accessTokenJwtUtil;

    @Autowired
    RefreshTokenJwtUtil refreshTokenJwtUtil;

    @Autowired
    CookieHandler cookieHandler;

    public User saveUser(UserWrapper userWrapper, HttpServletResponse httpServletResponse) {

        User user = new User(userWrapper.getUserName(), userWrapper.getFullName(), userWrapper.getEmail(),
                passwordEncoder.encode(userWrapper.getPassword()), userWrapper.getProfilePic(),
                userWrapper.getIsEmailVerified(), userWrapper.getDateOfBirth(),
                time.getGMT_Time("yyyy-MM-dd HH:mm:ss"));

        /*
         * Get the ip address from the request body and then process it and save it in the db  
         */
        
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        user.setIpAddress(ip);

        setAccessTokenInCookie(httpServletResponse, userWrapper.getUserName(), null, "/", 1000 * 60 * 30,
                "None");
        setRefreshTokenInCookie(httpServletResponse, userWrapper.getUserName(), null, "/", 1000 * 60 * 30,
                "None");

        return userRepository.save(user);
    }

    public ResponseBody authenticate(HttpServletResponse httpServletResponse, String userKey, String password) {

        ResponseBody responseBody = new ResponseBody();
        responseBody.setSource("/login");

        try {

            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userKey, password));

            if (authentication.isAuthenticated()) {

                setAccessTokenInCookie(httpServletResponse, userKey, null, "/", 1000 * 60 * 30,
                        "None");
                setRefreshTokenInCookie(httpServletResponse, userKey, null, "/", 1000 * 60 * 30,
                        "None");

                return new ResponseBody(true, 200, authentication.getPrincipal(), "Login successful", "/login");
            }

        } catch (UsernameNotFoundException | BadCredentialsException e) {
            responseBody.setSuccess(false);
            responseBody.setMessage("Invalid password");
            responseBody.setStatus(401);
        } catch (Exception e) {
            System.out.println(e);
            responseBody.setSuccess(false);
            responseBody.setMessage("Login failed");
            responseBody.setStatus(500);
        }

        return responseBody;

    }

    public void setAccessTokenInCookie(HttpServletResponse httpServletResponse, String userKey, String domain,
            String path, Integer durartionInMilliSec, String sameSite) {
        String accessJwtToken = accessTokenJwtUtil.generateToken(userKey);

        cookieHandler.setDesiredCookie(httpServletResponse, "access_token", accessJwtToken, domain, path,
                durartionInMilliSec,
                sameSite);
    }

    public void setRefreshTokenInCookie(HttpServletResponse httpServletResponse, String userKey, String domain,
            String path, Integer durartionInMilliSec, String sameSite) {
        String refreshJwtToken = refreshTokenJwtUtil.generateToken(userKey);

        cookieHandler.setDesiredCookie(httpServletResponse, "refresh_token", refreshJwtToken, null, "/", 1000 * 60 * 30,
                "None");
    }

}
