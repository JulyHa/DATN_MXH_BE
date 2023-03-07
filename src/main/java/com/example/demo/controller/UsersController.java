package com.example.demo.controller;

import com.example.demo.model.Users;
import com.example.demo.service.userService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
public class UsersController {
    @Autowired
    private IUserService iUserService;

    @GetMapping("/getAll")
    public ResponseEntity<Iterable<Users>> getUsers(){
        Iterable<Users> usersList = iUserService.findAll();
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id){
        Optional<Users> users = iUserService.findById(id);
        if(users.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users.get(), HttpStatus.OK);
    }

    @GetMapping("/friend/{id}")
    public ResponseEntity<Iterable<Users>> getFriendById(@PathVariable Long id){
        Iterable<Users> users = iUserService.findFriendRequestsByIdAndStatusTrue(id);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> save(@RequestBody Users user, @PathVariable("id") Long id) {
        Users usersOptional = iUserService.update(user, id);
        if(usersOptional == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(usersOptional, HttpStatus.OK);
    }

}
