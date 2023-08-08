package com.example.airbnb.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name= "notification_friend", schema = "be_socaialv")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationFriend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_send_id")
    private Users usersSend;
    @ManyToOne
    @JoinColumn(name = "user_receive_id")
    private Users usersReceive;
    @ManyToOne
    @JoinColumn(name = "notificationType_id")
    private NotificationType notificationType;

}
