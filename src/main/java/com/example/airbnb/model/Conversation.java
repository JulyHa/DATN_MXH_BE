package com.example.airbnb.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean type;// type = true là cuộc hội thoại cá nhân, type = false là cuộc hội thoại nhóm
    private String name; // tên hội thoại
    private boolean status;

}
