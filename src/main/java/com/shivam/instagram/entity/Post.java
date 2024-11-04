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
public class Post {
    
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    Integer postId;
    String caption;
    String bgMusic ;
    String location;
    Integer shareCount;
    boolean isArchived ;
    boolean isPublic ;
    boolean isTrashed ;
    String media;
    boolean isReactionCountsPublic ;
    boolean isCommentTurnedOn ;
    boolean isPostPinned ;

    String userId;
    
    String createdAt;
    String updatedAt ;
}
