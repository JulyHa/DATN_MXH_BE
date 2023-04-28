package com.example.airbnb.service;

import com.example.airbnb.model.Comments;
import com.example.airbnb.model.Likes;
import com.example.airbnb.model.Posts;

import java.util.Optional;

public interface ILikeService extends IGeneralService<Likes> {
    // post
    Optional<Likes> findPostLike(Long userId, Long postId);
    Long countPostLike(Long postId);
    void deleteAllByPost(Posts post);
    void likePost(Long id1, Long id2);
    void unLikePost(Long id1, Long id2);

    // comment
    Integer countCommentLike(Long commentId);
    Optional<Likes> findCommentLike(Long userId, Long commentId);
    void likeComment(Long id1, Long id2);
    void unLikeComment(Long id1, Long id2);
}
