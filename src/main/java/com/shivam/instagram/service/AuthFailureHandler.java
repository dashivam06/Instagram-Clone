// package com.shivam.instagram.service;

// import java.io.IOException;

// import org.springframework.security.authentication.BadCredentialsException;
// import org.springframework.security.authentication.DisabledException;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.web.authentication.AuthenticationFailureHandler;
// import org.springframework.stereotype.Component;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.shivam.instagram.dto.ResponseBody;

// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;


// @Component
// public class AuthFailureHandler implements AuthenticationFailureHandler 
// public class AuthFailureHandler implements AuthenticationFailureHandler 
// {

//     ResponseBody responseBody ;

//     ObjectMapper objectMapper = new ObjectMapper();

//     String message = "Invalid username or password";
//     Integer status = 403;
//     String url ;

//     @Override
//     public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
//             AuthenticationException exception) throws IOException, ServletException {

//         url = request.getRequestURL().toString();

//         if (exception instanceof UsernameNotFoundException) {
//             message = "User not found";
//         } else if(exception instanceof BadCredentialsException) {
//             message = "Invalid password";
//         }else if(exception instanceof DisabledException) {
//             message = "Your account is disabled. Please contact support.";
//         }

//         System.out.println("In here : Auth Failure ");

//         responseBody = new ResponseBody(false, status, null, message, url);

//         response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//         response.setContentType("application/json");
//         response.getWriter().write(objectMapper.writeValueAsString(responseBody));
//         return ;
        
//     }

// }
