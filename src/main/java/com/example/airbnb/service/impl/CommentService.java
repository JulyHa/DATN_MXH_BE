package com.example.airbnb.service.impl;

import com.example.airbnb.model.Comments;
import com.example.airbnb.model.Posts;
import com.example.airbnb.repository.ICommentRepository;
import com.example.airbnb.repository.ILikeRepository;
import com.example.airbnb.repository.IPostRepository;
import com.example.airbnb.service.ICommentService;
import com.example.airbnb.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Service
public class CommentService implements ICommentService {
    @Autowired
    private IPostRepository postRepository;
    @Autowired
    private ICommentRepository commentRepository;
    @Autowired
    private ILikeRepository likeRepository;
    @Autowired
    private INotificationService notificationService;
    @Override
    public Long countPostComment(Long postId) {
        return postRepository.findById(postId).get().getCountComment();
    }

    @Override
    public List<Comments> findAllByPost(Posts post) {
        return commentRepository.findAllByPost(post.getId());
    }

    @Override
    public void deleteAllByPost(Posts post) {
        for (Comments comment: findAllByPost(post)){
            likeRepository.deleteAllByIdComment(comment);
        }
        commentRepository.deleteAllByIdParentAndStatusTrue(post.getId());
    }

    @Override
    public Long countUserCommentPost(Long postId) {
        return commentRepository.countUserCommentPost(postId);
    }

    @Override
    public Iterable<Comments> findAll() {
        return null;
    }

    @Override
    public Optional<Comments> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    @Transactional
    public void save(Comments comment) {
        Posts post = postRepository.findById(comment.getIdParent()).get();
        if ((comment.getId() == null) && (!post.getUsers().getId().equals(comment.getUsers().getId()))){
            notificationService.deleteNotification(post.getId(), 3L);
            notificationService.createNotification(comment.getUsers().getId(), comment.getId(), 3L);
        }
        commentRepository.save(comment);
    }

    @Override
    public void remove(Long id) {

    }
}
