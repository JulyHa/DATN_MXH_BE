package com.example.airbnb.model;

import javax.persistence.*;

import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    private LocalDateTime sendTime;
    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

}
