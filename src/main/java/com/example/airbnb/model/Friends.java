package com.example.airbnb.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Friends {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_request")
    private Users userRequest;
    @ManyToOne
    @JoinColumn(name = "user_receive")
    private Users userReceive;
    @ManyToOne
    @JoinColumn(name = "friend_type")
    private FriendType friendType;
    // Có 4 dạng:
    // chờ xác nhận(Wait For Confirmation)
    // xác nhận(Confirm)
    // theo dõi(Follow)
    // chặn(Block)


}
