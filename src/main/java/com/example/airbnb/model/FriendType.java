package com.example.airbnb.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class FriendType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    // Có 4 dạng:
    // chờ xác nhận(Wait For Confirmation)
    // xác nhận(Confirm)
    // theo dõi(Follow)
    // chặn(Block)
}
