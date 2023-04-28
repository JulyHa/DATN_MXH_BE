package com.example.airbnb.repository;

import com.example.airbnb.model.Friends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IFriendRepository extends JpaRepository<Friends, Long> {
    @Query(value = "select * from friends where user_request = ?1 and user_receive = ?2 and status = true" , nativeQuery = true)
    Optional<Friends> findFriendRequest(Long id1, Long id2);
    @Query(value = "select * from friends where user_request = ?1 and user_receive = ?2 and status = false" , nativeQuery = true)
    Optional<Friends> findRequest(Long id1, Long id2);
    @Modifying
    @Query(value = "delete from friends where user_request = ?1 and user_receive = ?2", nativeQuery = true)
    void deleteFriendRequest(Long id1, Long id2);
    @Modifying
    @Query(value = "insert into friends(user_request, user_receive, status) values(?1, ?2, true)", nativeQuery = true)
    void acceptFriendRequest(Long id1, Long id2);
    @Query(value = "COUNT(status=true) from friends where user_receive=?1",nativeQuery = true)
    int countFriend(Long id);

}
