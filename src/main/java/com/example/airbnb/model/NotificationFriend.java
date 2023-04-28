package com.example.airbnb.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationFriend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "userSend_id")
    private Users usersSend;
    @ManyToOne
    @JoinColumn(name = "userReceive_id")
    private Users usersReceive;
    @ManyToOne
    @JoinColumn(name = "notificationType_id")
    private NotificationType notificationType;

}
