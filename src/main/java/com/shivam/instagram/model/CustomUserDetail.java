package com.shivam.instagram.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.shivam.instagram.entity.User;

public class CustomUserDetail implements UserDetails
{

    private final User user ;

    public CustomUserDetail(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        // List<SimpleGrantedAuthority> authorities = this.user.getRole()
        //                                                     .stream()
        //                                                     .map(role -> new SimpleGrantedAuthority(role))
        //                                                     .collect(Collectors.toList());



        List<SimpleGrantedAuthority> authorities =  Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));

        return authorities ;


    }

    @Override
    public String getPassword() {

        String password = this.user.getPassword();

        return password;
    }

    @Override
    public String getUsername() {
        
        String userName = this.user.getUserName();

        return userName;
    }
    
    public String getEmail() {
        
        String email = this.user.getEmail();

        return email;
    }
}
