package com.example.airbnb.service;

import com.example.airbnb.dto.RegistrationRequest;
import com.example.airbnb.model.Users;

import javax.mail.MessagingException;

public interface IAuthService {
    boolean registerUser(RegistrationRequest registrationRequest) throws MessagingException;
    boolean verifyOTP(String otp);
}
