package com.example.airbnb.repository;

import com.example.airbnb.model.Notifications;
import com.example.airbnb.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INotificationRepository extends JpaRepository<Notifications, Long> {
    void deleteAllByPost(Posts post);
    @Modifying
    @Query(value = "select * from notifications noti join posts p on noti.post_id = p.id " +
            "where p.user_id = ?1 and (noti.noti_type_id = 3 or noti.noti_type_id = 4)", nativeQuery = true)
    List<Notifications> getAll(Long userId);

    @Modifying
    @Query(value = "select * from notifications noti where noti.noti_type_id = 2", nativeQuery = true)
    List<Notifications> getAllNewFriendPost(Long userId);

    @Modifying
    @Query(value = "delete from notifications where post_id = ?1 and noti_type_id = ?2", nativeQuery = true)
    void deleteNotification(Long postId, Long typeId);

    @Modifying
    @Query(value = "insert into notifications(user_id, post_id, noti_type_id, notification_at, status) values(?1, ?2, ?3, time(now()), false)", nativeQuery = true)
    void createNotification(Long userId, Long postId, Long typeId);
}
