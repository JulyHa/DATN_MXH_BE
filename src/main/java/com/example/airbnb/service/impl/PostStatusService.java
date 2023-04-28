package com.example.airbnb.service.impl;

import com.example.airbnb.model.PostStatus;
import com.example.airbnb.repository.IPostStatusRepository;
import com.example.airbnb.service.IPostStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class PostStatusService implements IPostStatusService {
    @Autowired
    private IPostStatusRepository postStatusRepository;

    @Override
    public Iterable<PostStatus> findAll() {
        return postStatusRepository.findAll();
    }

    @Override
    public Optional<PostStatus> findById(Long id) {
        return postStatusRepository.findById(id);
    }

    @Override
    public void save(PostStatus postStatus) {
        postStatusRepository.save(postStatus);
    }

    @Override
    public void remove(Long id) {
        postStatusRepository.deleteById(id);
    }
}
