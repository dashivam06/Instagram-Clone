package com.shivam.instagram.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shivam.instagram.controller.UserWrapper;
import com.shivam.instagram.entity.User;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>
{

    @Query("SELECT u FROM User u WHERE u.userName = :login OR u.email = :login")
    Optional<User> findByUsernameOrEmail(@Param("login") String loginID);

    Optional<User> findByEmail(String email);

    Optional<User> findByUserName(String username);

    @Transactional
    Optional<User> deleteByUserName(String username);
    

}
