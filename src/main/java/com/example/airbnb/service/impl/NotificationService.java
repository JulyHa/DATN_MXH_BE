package com.example.airbnb.service.impl;

import com.example.airbnb.model.Notifications;
import com.example.airbnb.model.Posts;
import com.example.airbnb.repository.INotificationRepository;
import com.example.airbnb.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService implements INotificationService {
    @Autowired
    private INotificationRepository notificationRepository;
    @Override
    public Iterable<Notifications> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Optional<Notifications> findById(Long id) {
        return notificationRepository.findById(id);
    }

    @Override
    public void save(Notifications notifications) {
        notificationRepository.save(notifications);
    }

    @Override
    public void remove(Long id) {
        Notifications notifications = findById(id).get();
        notifications.setStatus(false);
        notificationRepository.save(notifications);
    }

    @Override
    public void deleteAllByPost(Posts posts) {
        notificationRepository.deleteAllByPost(posts);

    }

    @Override
    public List<Notifications> getAll(Long userId) {
        List<Notifications> notifications = notificationRepository.getAll(userId);
        List<Notifications> notifications1 = notificationRepository.getAllNewFriendPost(userId);
        notifications.addAll(notifications1);
        return notifications;
    }

    @Override
    public void deleteNotification(Long postId, Long typeId) {
        notificationRepository.deleteNotification(postId, typeId);

    }

    @Override
    @Transactional
    public void createNotification(Long userId, Long postId, Long typeId) {
        notificationRepository.createNotification(userId, postId, typeId);
    }
}
