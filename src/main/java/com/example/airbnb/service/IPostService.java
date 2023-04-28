package com.example.airbnb.service;

import com.example.airbnb.model.Posts;
import com.example.airbnb.model.Users;

import java.util.List;

public interface IPostService extends IGeneralService<Posts> {
    boolean update(Posts posts);
    List<Posts> findAllFriendPost(Long id);
    List<Posts> findAllPersonalPost(Users users);
    List<Posts> findAllFriendPublicPost(Long id);
    Iterable<Posts> findAllPostByUserIdAndContent(Long id ,String content);
}
