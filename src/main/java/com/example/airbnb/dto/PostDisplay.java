package com.example.airbnb.dto;

import com.example.airbnb.model.PostStatus;
import com.example.airbnb.model.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDisplay {
    private Long id;
    private Users users;
    private String content;
    private PostStatus postStatus;
    private LocalDateTime createAt;
    private boolean checkUserLiked;
}
