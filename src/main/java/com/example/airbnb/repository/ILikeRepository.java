package com.example.airbnb.repository;

import com.example.airbnb.model.Comments;
import com.example.airbnb.model.Likes;
import com.example.airbnb.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ILikeRepository extends JpaRepository<Likes, Long> {
    @Query(value = "select * from likes where user_id = ?1 and id_post = ?2", nativeQuery = true)
    Optional<Likes> findPostLike(Long userId, Long postId);
    void deleteAllByIdPost(Posts post);
    @Modifying
    @Query(value = "insert into likes(user_id, id_post) values(?1, ?2)", nativeQuery = true)
    void likePost(Long id1, Long id2);
    @Modifying
    @Query(value = "delete from likes where user_id = ?1 and id_post = ?2", nativeQuery = true)
    void unLikePost(Long id1, Long id2);

    // comment
    void deleteAllByIdComment(Comments comment);
    @Query(value = "select count(user_id) from likes where id_comment = ?1", nativeQuery = true)
    Integer countCommentLike(Long commentId);
    @Query(value = "select * from likes where user_id = ?1 and id_comment = ?2", nativeQuery = true)
    Optional<Likes> findCommentLike(Long userId, Long commentId);
    @Modifying
    @Query(value = "insert into likes(user_id, id_comment) values(?1, ?2)", nativeQuery = true)
    void likeComment(Long id1, Long id2);

    @Modifying
    @Query(value = "delete from likes where user_id = ?1 and id_comment = ?2", nativeQuery = true)
    void unLikeComment(Long id1, Long id2);

}
