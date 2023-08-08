package com.example.airbnb.service.impl;

import com.example.airbnb.model.Likes;
import com.example.airbnb.model.Posts;
import com.example.airbnb.repository.ILikeRepository;
import com.example.airbnb.repository.IPostRepository;
import com.example.airbnb.service.ILikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
@Service
public class LikeService implements ILikeService {
    @Autowired
    private ILikeRepository likeRepository;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private IPostRepository postRepository;

    @Override
    public Iterable<Likes> findAll() {
        return null;
    }

    @Override
    public Optional<Likes> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(Likes likes) {
        likeRepository.save(likes);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Optional<Likes> findPostLike(Long userId, Long postId) {
        return likeRepository.findPostLike(userId, postId);
    }

    @Override
    public Long countPostLike(Long postId) {
        return postRepository.findById(postId).get().getCountLikePost();
    }

    @Override
    public void deleteAllByPost(Posts post) {
        likeRepository.deleteAllByIdPost(post);
    }

    @Override
    @Transactional
    public void likePost(Long id1, Long id2) {
        Posts post = postRepository.findById(id2).get();
        post.setCountLikePost(post.getCountLikePost() + 1);
        postRepository.save(post);
        if (id1 != post.getUsers().getId()) {
//            notificationService.deleteNotification(id2, 3L);
            notificationService.createNotification(id1, id2, 3L); // thông báo
        }
        likeRepository.likePost(id1, id2);
    }


    @Override
    @Transactional
    public void unLikePost(Long id1, Long id2) {
        Posts posts = postRepository.findById(id2).get();
        posts.setCountLikePost(posts.getCountLikePost() -1);
        postRepository.save(posts);
        likeRepository.unLikePost(id1, id2);
    }

    @Override
    public Integer countCommentLike(Long commentId) {
        return likeRepository.countCommentLike(commentId);
    }

    @Override
    public Optional<Likes> findCommentLike(Long userId, Long commentId) {
        return likeRepository.findCommentLike(userId, commentId);
    }

    @Override
    @Transactional
    public void likeComment(Long id1, Long id2) {
        likeRepository.likeComment(id1, id2);
    }

    @Override
    @Transactional
    public void unLikeComment(Long id1, Long id2) {
        likeRepository.unLikeComment(id1, id2);
    }
}
