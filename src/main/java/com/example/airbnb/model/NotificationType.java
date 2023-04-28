package com.example.airbnb.model;


import javax.persistence.*;

import lombok.*;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NotificationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    // Type của thông báo: bài viết mới, bình luận, like, share, kết bạn, ...
    // 1 Kết bạn
    // 2: Đăng bài
}