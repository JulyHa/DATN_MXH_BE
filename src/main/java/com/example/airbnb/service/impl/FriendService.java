package com.example.airbnb.service.impl;

import com.example.airbnb.model.Friends;
import com.example.airbnb.repository.IFriendRepository;
import com.example.airbnb.service.IFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class FriendService implements IFriendService {
    @Autowired
    private IFriendRepository friendRepository;
    @Override
    public Optional<Friends> findFriendRequest(Long id1, Long id2) {
        return friendRepository.findFriendRequest(id1, id2);
    }

    @Override
    public Optional<Friends> findRequest(Long id1, Long id2) {
        return friendRepository.findRequest(id1,id2);
    }

    @Override
    @Transactional
    public void deleteFriendRequest(Long id1, Long id2) {
        friendRepository.deleteFriendRequest(id1, id2);
        friendRepository.deleteFriendRequest(id2, id1);
    }

    @Override
    @Transactional
    public void acceptFriendRequest(Long id1, Long id2) {
        deleteFriendRequest(id1, id2);
        friendRepository.acceptFriendRequest(id1, id2);
        friendRepository.acceptFriendRequest(id2, id1);
    }

    @Override
    public int countFriend(Long id) {
        return friendRepository.countFriend(id);
    }

    @Override
    public Iterable<Friends> findAll() {
        return null;
    }

    @Override
    public Optional<Friends> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(Friends friends) {
        friendRepository.save(friends);
    }

    @Override
    public void remove(Long id) {

    }
}
