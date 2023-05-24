package com.example.airbnb.dto;

import lombok.Data;

@Data
public class UserUpdate {
    private Long id;
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
}
