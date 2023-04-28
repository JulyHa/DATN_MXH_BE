package com.example.airbnb.service;

import com.example.airbnb.model.Comments;
import com.example.airbnb.model.Posts;

import java.util.List;

public interface ICommentService extends IGeneralService<Comments> {
    Long countPostComment(Long postId);
    List<Comments> findAllByPost(Posts post);
    void deleteAllByPost(Posts post);
    Long countUserCommentPost(Long postId);
}
