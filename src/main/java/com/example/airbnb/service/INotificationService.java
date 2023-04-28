package com.example.airbnb.service;

import com.example.airbnb.model.Notifications;
import com.example.airbnb.model.Posts;

import java.util.List;

public interface INotificationService extends IGeneralService<Notifications> {
    void deleteAllByPost(Posts posts);
    List<Notifications> getAll(Long userId);
    void deleteNotification(Long postId, Long typeId);
    void createNotification(Long userId, Long postId, Long typeId);
}