package com.example.demo.service.signUpService;

import com.example.demo.model.Users;

public interface ISignUpService {
    boolean signUp(Users users);
    boolean checkUserExist(Users users);
}
