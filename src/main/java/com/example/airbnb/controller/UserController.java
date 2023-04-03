package com.example.airbnb.controller;

import com.example.airbnb.model.Users;
import com.example.airbnb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@PropertySource("classpath:application.properties")
@CrossOrigin("*")
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/")
    public ResponseEntity<Iterable<Users>> showAllUser() {
        Iterable<Users> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping("/admin")
    public ResponseEntity<Iterable<Users>> showAllUserByAdmin() {
        Iterable<Users> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getProfile(@PathVariable Long id) {
        Optional<Users> userOptional = this.userService.findById(id);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUserProfile(@PathVariable Long id, @RequestBody Users user) {
        Optional<Users> userOptional = this.userService.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setId(userOptional.get().getId());
        user.setEnabled(userOptional.get().isEnabled());
        user.setPassword(userOptional.get().getPassword());
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
