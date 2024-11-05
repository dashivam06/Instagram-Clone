package com.shivam.instagram.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.shivam.instagram.jwt.JwtFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig 
{

    @Autowired
    CorsConfig corsConfig ;

    @Autowired 
    JwtFilter jwtFilter;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http ) throws Exception
    {
        
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfig))
            .exceptionHandling(exception -> System.out.println("Exception  :  "+ exception))
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        http   
            .authorizeHttpRequests(request -> 
                request
                .requestMatchers("/admin","/admin/**").hasRole("ADMIN")
                .requestMatchers("/sign-in","/sign-up","/generate","getAllUser","create-cr7","del-cr7").permitAll()
                .requestMatchers("/locked").authenticated()
                    .anyRequest().permitAll());

        http
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        http    
            .httpBasic(Customizer.withDefaults());

        
            return http.build();
        
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager getAuthenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


}
