package com.shivam.instagram.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Followings {
    
     @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    Integer followingId;

    Integer followerId;
    Integer followeeId;

    String createdAt;
    String updatedAt ;
}
