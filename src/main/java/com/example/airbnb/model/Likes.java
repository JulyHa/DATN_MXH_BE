package com.example.airbnb.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.*;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id") // người like
    private Users users;

    @ManyToOne
    @JoinColumn(name = "id_post")
    private Posts idPost; // id của post

    @ManyToOne
    @JoinColumn(name = "id_comment")
    private Comments idComment; // id của comment
}
