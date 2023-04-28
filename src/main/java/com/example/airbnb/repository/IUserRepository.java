package com.example.airbnb.repository;


import com.example.airbnb.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface IUserRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email);
    Optional<Users> findByVerificationCode(String otp);
    @Query(value = "select * from users join friends fr on users.id = fr.user_receive " +
            "where fr.user_request =?1 and fr.status = true"
            , nativeQuery = true)
    List<Users> findFriendRequestsByIdAndStatusTrue(Long id);
    @Query(value = "select * from users join likes on users.id = likes.user_id where likes.id_post = ?1", nativeQuery = true)
    List<Users> findAllLikePost(Long id);
    List<Users>findUsersByFirstNameOrLastNameContainingAndEnabledIsTrue(String firstName, String lastName);
    @Query(value = "select * from users join friends fr on users.id = fr.user_request  where fr.user_receive = ?1 and fr.status = 2", nativeQuery = true)
    List<Users> listFriendRequest(Long id);
    @Query(value = "select * from users join conversation_member cm on users.id = cm.user_id where cm.conversation_id = ?1", nativeQuery = true)
    List<Users> findMemberByConversation(Long id);
}