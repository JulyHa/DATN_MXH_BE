package com.example.airbnb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
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
    @ManyToOne
    @JoinColumn(name = "audience_id")
    private PostAudience postAudience;
    private Long countLikePost;
    private Long countComment;
    private boolean commentPermission;  //default true: cho người lạ comment
    private boolean status;
}
