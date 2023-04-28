package com.example.airbnb.repository;

import com.example.airbnb.model.Posts;
import com.example.airbnb.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPostRepository extends JpaRepository<Posts, Long> {
    @Query(value = "select * from posts where user_id = ?1 and (post_status_id = 1 or post_status_id = 2)", nativeQuery = true)
    List<Posts> findAllFriendPost(Long id);
    List<Posts> findAllByUsers(Users users);
    @Query(value = "select * from posts where user_id = ?1 and post_status_id = 1", nativeQuery = true)
    List<Posts> findAllFriendPublicPost(Long id);
    Iterable<Posts> findAllByStatus(boolean status);
    Iterable<Posts>findPostsByUsers_IdAndContentContaining(Long id,String content);
}
