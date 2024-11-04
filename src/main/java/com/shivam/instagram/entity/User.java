package com.shivam.instagram.entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Getter
@Table(name = "user_table")
public class User 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer userId ;
    String userName;
    String fullName ;
    String email ;
    String password;
    String profilePic ;
    String bio ;
    String role ;
    String status ;
    boolean isBlueTickEnabled;
    boolean isEmailVerified ;
    boolean isPrivateAccount;
    String dateOfBirth ;
    String updatedAt ;
    String createdAt ;
    List<String> savedPost ;
    String ipAddress;


    
    public User(String userName, String fullName, String email, String password, String profilePic,
            boolean emailVerified, String dateOfBirth, String currentGMT_Time) {


        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.profilePic = profilePic;
        this.isEmailVerified = emailVerified;
        this.dateOfBirth = dateOfBirth;

        this.status = "Active";
        this.isPrivateAccount = false;
        this.isBlueTickEnabled = false;
        this.createdAt = currentGMT_Time;
        this.updatedAt = currentGMT_Time;

        this.savedPost = new ArrayList<>();
        this.role = "USER";
    }



    
    
    
}
