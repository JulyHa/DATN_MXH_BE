package com.example.airbnb.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_recipient_id")
    private Users userRecipient;
    @ManyToOne
    @JoinColumn(name = "user_send_id")
    private Users userSender;
    private String content;
    @ManyToOne
    @JoinColumn(name = "noti_type_id")
    private NotificationType notificationType; // bài viết mới, bình luận, like, share, kết bạn, ...
    private LocalDateTime notificationAt;
    private boolean status; //false-chưa đọc tb, true-đã đọc tb (default false)
}
