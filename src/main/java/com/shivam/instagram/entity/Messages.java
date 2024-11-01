package com.shivam.instagram.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Messages 
{

     @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    Integer messageId ;

    String senderId ;
    String receiverId;

    String createdAt;
    String updatedAt ;
}
