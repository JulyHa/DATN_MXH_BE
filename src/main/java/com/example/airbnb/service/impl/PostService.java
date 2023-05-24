package com.example.airbnb.service.impl;

import com.example.airbnb.model.Posts;
import com.example.airbnb.model.Users;
import com.example.airbnb.repository.IPostRepository;
import com.example.airbnb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService implements IPostService {
    @Autowired
    private IPostRepository postRepository;
    @Autowired
    private IPostImageService imagePostService;
    @Autowired
    private INotificationService notificationService;
    @Autowired
    private ILikeService iLikeService;
    @Autowired
    private ICommentService iCommentService;
    @Override
    public Iterable<Posts> findAll() {
        return postRepository.findAllByStatus(true);
    }

    @Override
    public Optional<Posts> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    @Transactional
    public void save(Posts posts) {
        posts.setCreateAt(LocalDateTime.now());
        posts.setStatus(true);
        posts.setCountLikePost(0L);
        posts.setCountComment(0L);
        posts.setStatus(true);
         postRepository.save(posts);
        if (posts.getId() != null){
            if (posts.getPostStatus().getId() != 3) {
                notificationService.createNotification(posts.getUsers().getId(), posts.getId(), 1L);
            }
        }

    }
    @Override
    @Transactional
    public boolean update(Posts posts) {
        Optional<Posts> postOptional = postRepository.findById(posts.getId());
        if(!postOptional.isPresent()){
            return false;
        }
        if(posts.getContent() != null){
            postOptional.get().setContent(posts.getContent());
        }
        if(posts.getPostStatus() != null) {
            postOptional.get().setPostStatus(posts.getPostStatus());
        }
        postRepository.save(postOptional.get());
        return true;
    }

    @Override
    @Transactional
    public void remove(Long id) {
        iLikeService.deleteAllByPost(findById(id).get());
        iCommentService.deleteAllByPost(findById(id).get());
        notificationService.deleteAllByPost(findById(id).get());
        imagePostService.deleteAllByPosts(findById(id).get());
        postRepository.deleteById(id);
    }

    @Override
    public List<Posts> findAllFriendPost(Long id) {
        return postRepository.findAllFriendPost(id);
    }

    @Override
    public List<Posts> findAllPersonalPost(Users users) {
        return postRepository.findAllByUsers(users);
    }

    @Override
    public List<Posts> findAllFriendPublicPost(Long id) {
        return postRepository.findAllFriendPublicPost(id);
    }

    @Override
    public Iterable<Posts> findAllPostByUserIdAndContent(Long id, String content) {
        return postRepository.findPostsByUsers_IdAndContentContaining(id, content);
    }
}
