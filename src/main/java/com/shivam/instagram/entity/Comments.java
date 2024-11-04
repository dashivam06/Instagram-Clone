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
public class Comments 
{
     @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    Integer commentId ;
    String content ;
    Integer likesCount;
    
    Integer userId;
    Integer postId;
    
    String createdAt;
    String updatedAt ;
}
