package com.example.airbnb.service;

import com.example.airbnb.model.PostImage;
import com.example.airbnb.model.Posts;

import java.util.List;

public interface IPostImageService extends IGeneralService<PostImage>{
    List<PostImage> findAllByPost(Posts post);
    void deleteAllByPosts(Posts posts);
}
