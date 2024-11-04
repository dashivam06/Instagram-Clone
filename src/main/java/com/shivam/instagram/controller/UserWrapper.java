package com.shivam.instagram.controller;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Getter
public class UserWrapper 
{
    String userName;
    String fullName ;
    String email ;
    String password;
    String profilePic ;
    Boolean isEmailVerified;
    String dateOfBirth ;

    public UserWrapper(String userName, String fullName, String email, String password, String profilePic,
            boolean emailVerified, String dateOfBirth) { 
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.profilePic = profilePic;
        this.isEmailVerified = emailVerified;
        this.dateOfBirth = dateOfBirth;


    }
    
}
