package com.example.airbnb.model;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
    private String content;
    private LocalDateTime createAt;
    @Value("0")
    private Long countLikePost;
    @Value("0")
    private Long countComment;
    @ManyToOne
    @JoinColumn(name = "postStatus_id")
    private PostStatus postStatus; // 1_Public, 2_Friend, 3_Private
    @Value("true")
    private boolean status; //true_hiển thị, false_xóa
}
