package com.example.airbnb.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.io.Serializable;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Pattern(regexp = "^(.+)@(\\S+)$")
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @NotNull(message = "Không được bỏ trống")
    private String firstName;
    @NotNull(message = "Không được bỏ trống")
    private String lastName;
    @Pattern(regexp = "^[0-9]{10}$",message = "Phải là số có 10 số")
    private String phone;
    private String gender;
    private LocalDate birthday;
    private String avatar;
    private String address;
    private String hobby;
    private boolean enabled = true; //Trạng thái tài khoản (block or active)
    private boolean seeFriendPermission = true; // default true: cho xem bạn bè

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;


    public Users(String email, String password,  Set<Role> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Users() {
    }

}
