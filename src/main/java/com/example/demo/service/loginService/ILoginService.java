package com.example.demo.service.loginService;

import com.example.demo.dao.UserUpdate;
import com.example.demo.model.Users;

public interface ILoginService {
    Users login(Users users);
    boolean changePassword(UserUpdate userUpdate);
}
