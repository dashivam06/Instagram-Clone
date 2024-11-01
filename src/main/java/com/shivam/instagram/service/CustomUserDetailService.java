package com.shivam.instagram.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import com.shivam.instagram.entity.User;
import com.shivam.instagram.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String loginID)  {

        User user = userRepository.findByUserName(loginID).orElseThrow(() -> new UsernameNotFoundException("User not found"));

            return org.springframework.security.core.userdetails.User
                    .withUsername(loginID)
                    .password(user.getPassword())
                    .roles(user.getRole())
                    .build();
        
    }

    public UserDetails loadUserByEmail(String email)  {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

            return org.springframework.security.core.userdetails.User
                    .withUsername(email)
                    .password(user.getPassword())
                    .roles(user.getRole())
                    .build();
        
    }



    public UserDetails loadUserByUsernameOrEmail(String userKey)  {

        User user = userRepository.findByUsernameOrEmail(userKey).orElseThrow(() -> new UsernameNotFoundException("User not found"));

            return org.springframework.security.core.userdetails.User
                    .withUsername(userKey)
                    .password(user.getPassword())
                    .roles(user.getRole())
                    .build();
        
    }


  
}
