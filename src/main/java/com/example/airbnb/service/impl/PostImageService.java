package com.example.airbnb.service.impl;

import com.example.airbnb.model.PostImage;
import com.example.airbnb.model.Posts;
import com.example.airbnb.repository.IPostImageRepository;
import com.example.airbnb.service.IPostImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostImageService implements IPostImageService {
    @Autowired
    private IPostImageRepository postImageRepository;
    @Override
    public Iterable<PostImage> findAll() {
        return postImageRepository.findAll();
    }

    @Override
    public Optional<PostImage> findById(Long id) {
        return postImageRepository.findById(id);
    }

    @Override
    public void save(PostImage postImage) {
        postImageRepository.save(postImage);
    }

    @Override
    public void remove(Long id) {
        postImageRepository.deleteById(id);
    }

    @Override
    public List<PostImage> findAllByPost(Posts post) {
        return postImageRepository.findAllByPosts(post);
    }

    @Override
    public void deleteAllByPosts(Posts posts) {
        postImageRepository.deleteAllByPosts(posts);
    }
}
