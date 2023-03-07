package com.example.demo.repository;

import com.example.demo.model.Users;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepo extends JpaRepository<Users, Long> {
    Optional<Users> findUsersByUsername(String username);
    @Query(value = "select * from users join friend_request fr on users.id = fr.user_receive_id " +
            "where fr.user_request_id =?1 and fr.status = true"
            , nativeQuery = true)
    Iterable<Users> findFriendRequestsByIdAndStatusTrue(Long id);

}
