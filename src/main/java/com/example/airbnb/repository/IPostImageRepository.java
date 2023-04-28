package com.example.airbnb.repository;

import com.example.airbnb.model.PostImage;
import com.example.airbnb.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPostImageRepository extends JpaRepository<PostImage, Long> {
    List<PostImage> findAllByPosts(Posts posts);
    void deleteAllByPosts(Posts post);
}
