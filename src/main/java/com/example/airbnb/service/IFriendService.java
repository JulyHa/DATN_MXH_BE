package com.example.airbnb.service;

import com.example.airbnb.model.Friends;

import java.util.Optional;

public interface IFriendService extends IGeneralService<Friends> {
    Optional<Friends> findFriendRequest(Long id1, Long id2);
    Optional<Friends> findRequest(Long id1, Long id2);
    void deleteFriendRequest(Long id1, Long id2);
    void acceptFriendRequest(Long id1, Long id2);
    int countFriend(Long id);
}
