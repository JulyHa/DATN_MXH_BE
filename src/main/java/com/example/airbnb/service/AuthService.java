package com.example.airbnb.service;

import com.example.airbnb.dto.RegistrationRequest;
import javax.mail.MessagingException;

public interface AuthService {
    boolean registerUser(RegistrationRequest registrationRequest) throws MessagingException;
    boolean verifyOTP(String otp);
}
