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
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDateTime createAt;
    private Long countLike;
    @JoinColumn(name = "id_parent")
    private Long idParent; // id của post hoặc comment
    @JoinColumn(name = "parent_type")
    private boolean parentType; // true-post, false-comment
    @OneToOne
    @JoinColumn(name = "user_id")
    private Users users;
    private boolean status;
}
