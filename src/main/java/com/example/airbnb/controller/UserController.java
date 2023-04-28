package com.example.airbnb.controller;

import com.example.airbnb.model.Users;
import com.example.airbnb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@PropertySource("classpath:application.properties")
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService userService;


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

//    @GetMapping("/{id}")
//    public ResponseEntity<Users> getProfile(@PathVariable Long id) {
//        Optional<Users> userOptional = this.userService.findById(id);
//        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

    @GetMapping("/friend/{id}")
    public ResponseEntity<List<Users>> findAllFriend(@PathVariable Long id) {
        List<Users> users = userService.findFriendRequestsByIdAndStatusTrue(id);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> save(@RequestBody Users users, @PathVariable("id") Long id) {
        Optional<Users> userCurrent = userService.findById(id);
        if (!userCurrent.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        users.setId(userCurrent.get().getId());
        if (users.getAvatar() == null) {
            users.setAvatar(userCurrent.get().getAvatar());
        }
        users.setPassword(userCurrent.get().getPassword());
        users.setEnabled(userCurrent.get().isEnabled());
        users.setRoles(userCurrent.get().getRoles());
        userService.save(users);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping("/search")
    ResponseEntity<List<Users>>findUsersByNameContain(@RequestParam ("search") String name){
        List<Users> usersList=userService.findUsersActiveByName(name);
        return new ResponseEntity<>(usersList,HttpStatus.OK);
    }
}
