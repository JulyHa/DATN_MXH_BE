package com.example.demo.service.userService;

import com.example.demo.model.Users;
import com.example.demo.service.IGeneralService;

public interface IUserService extends IGeneralService<Users> {
    Users update(Users users, Long id);
    Users findUserByUsername(String username);
    Users setSeeComment(Users user);
    Iterable<Users> findFriendRequestsByIdAndStatusTrue(Long id);
}
