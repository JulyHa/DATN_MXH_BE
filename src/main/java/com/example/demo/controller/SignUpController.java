package com.example.demo.controller;

import com.example.demo.model.Users;
import com.example.demo.service.signUpService.ISignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class SignUpController {
    @Autowired
    private ISignUpService iSignUpService;
    @PostMapping("/signUp")
    public ResponseEntity<Users> signUp(@RequestBody Users users) {
        if (iSignUpService.signUp(users)) {
            return new ResponseEntity<>(users, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
