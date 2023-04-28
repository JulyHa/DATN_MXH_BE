package com.example.airbnb.service;

import com.example.airbnb.model.Users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface IUserService extends UserDetailsService {
    void save(Users user);
    Iterable<Users> findAll();
    Users findByEmail(String email);
    Users getCurrentUser();
    Optional<Users> findById(Long id);
    UserDetails loadUserById(Long id);
    boolean checkLogin(Users user);
    boolean checkUser(Users user);
    boolean isRegister(Users user);
    List<Users> findFriendRequestsByIdAndStatusTrue(Long id);
    List<Users> findUsersActiveByName(String name);
    public List<Users> listFriendRequest(Long id);
    List<Users> findAllLikePost(Long id);
    List<Users> findInListFriend(Long id, String q);
    List<Users> findMemberByConversation(Long id);
}
