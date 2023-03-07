package com.example.demo.controller;

import com.example.demo.dao.UserUpdate;
import com.example.demo.model.Users;
import com.example.demo.service.loginService.ILoginService;
import com.example.demo.service.userService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class LoginController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private ILoginService iLoginService;

    @PostMapping("/login")
    public ResponseEntity<Users> login(@RequestBody Users users){
        Users user = iLoginService.login(users);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Users> logOut(@RequestBody Users userLogOut){
        Users user = iUserService.findUserByUsername(userLogOut.getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("changePw")
    public ResponseEntity<?> changePassword(@RequestBody UserUpdate userUpdate){
        if (iLoginService.changePassword(userUpdate)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/permission")
    public ResponseEntity<Users> setSeeComment(@RequestBody Users users){
        Users usersSetSeeComment = iUserService.setSeeComment(users);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
