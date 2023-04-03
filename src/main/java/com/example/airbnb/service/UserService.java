package com.example.airbnb.service;

import com.example.airbnb.model.Users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    void save(Users user);

    Iterable<Users> findAll();

    Users findByEmail(String email);

    Users getCurrentUser();

    Optional<Users> findById(Long id);

    UserDetails loadUserById(Long id);

    boolean checkLogin(Users user);

    boolean isRegister(Users user);

//    boolean isCorrectConfirmPassword(User user);
}
