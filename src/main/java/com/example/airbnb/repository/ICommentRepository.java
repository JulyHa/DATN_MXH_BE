package com.example.airbnb.repository;

import com.example.airbnb.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepository extends JpaRepository<Comments, Long> {

//    @Query(value = "select count(user_id) from post_comment where post_id = ?1", nativeQuery = true)
//    Integer countPostComment(Long postId);
    @Query(value = "select * from comments where id_parent = ?1 and status = 1", nativeQuery = true)
    List<Comments> findAllByPost(Long idPost);
    void deleteAllByIdParentAndStatusTrue(Long idPost);
    @Query(value = "SELECT count(DISTINCT user_id) FROM post_comment where post_id = ?1", nativeQuery = true)
    Long countUserCommentPost(Long postId);
}
