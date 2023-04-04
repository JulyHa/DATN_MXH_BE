package com.example.airbnb.controller;

import com.example.airbnb.dto.JwtResponse;
import com.example.airbnb.dto.RegistrationRequest;
import com.example.airbnb.model.Users;
import com.example.airbnb.service.AuthService;
import com.example.airbnb.service.RoleService;
import com.example.airbnb.service.UserService;
import com.example.airbnb.service.impl.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@PropertySource("classpath:application.properties")
@CrossOrigin("*")
public class AccountController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Users> createUser(@RequestBody RegistrationRequest registrationRequest, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            boolean result = authService.registerUser(registrationRequest);
            if(!result){
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (MessagingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/verify-otp")
    public ResponseEntity<?> verifyOTP(@RequestParam String otp) {
        if (authService.verifyOTP(otp)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Users currentUser = userService.findByEmail(user.getEmail());
        return ResponseEntity.ok(new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(), userDetails.getAuthorities()));
    }
}
