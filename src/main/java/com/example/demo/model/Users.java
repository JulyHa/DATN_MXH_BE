package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.time.LocalDate;

@Valid
@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Pattern(regexp="^[a-zA-Z0-9]*$",message = "Tên chứa ký tự đặc biệt!")
    private String username;
    @Size(min = 6,max = 32,message = "password từ 6-32 ký tự")
    @Pattern(regexp="^[a-zA-Z0-9]*$",message = "Tên chứa ký tự đặc biệt!")
    private String password;
    @NotNull(message = "không được bỏ trống")
    private String fullName;
    @Pattern(regexp = "^(.+)@(\\S+)$")
    private String email;
    @Pattern(regexp = "^[0-9]{10}$",message = "phải là số có 10 số")
    private String phone;
    @NotNull(message = "không được bỏ trống")
    private LocalDate birthday;
    private String avatar;
    private String address;
    private String hobby;
    private boolean status; //Trạng thái tài khoản (block or active)
    private boolean checkOn; // Trạng thái đăng nhập
    private boolean seeFriendPermission; // default true: cho xem bạn bè
    private boolean commentPermission;  //default true: cho người lạ comment
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
