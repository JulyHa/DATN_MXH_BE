package com.example.airbnb.model;

import javax.persistence.*;

import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Posts post;
    @ManyToOne
    @JoinColumn(name = "noti_type_id")
    private NotificationType notificationType; // bài viết mới, bình luận, like, share, kết bạn, ...
    private LocalDateTime notificationAt;
    private boolean status; //false-chưa đọc tb, true-đã đọc tb (default false)
}
