package com.example.airbnb.service.impl;

import com.example.airbnb.model.Users;
import com.example.airbnb.dto.UserPrinciple;
import com.example.airbnb.repository.UserRepository;
import com.example.airbnb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        Users user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        if (this.checkLogin(user)) {
            return UserPrinciple.build(user);
        }
        boolean enable = false;
        boolean accountNonExpired = false;
        boolean credentialsNonExpired = false;
        boolean accountNonLocked = false;
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), enable, accountNonExpired, credentialsNonExpired,
                accountNonLocked, null);
    }


    @Override
    public void save(Users user) {
        userRepository.save(user);
    }

    @Override
    public Iterable<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Users findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Users getCurrentUser() {
        Users user;
        String email;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        user = this.findByEmail(email);
        return user;
    }

    @Override
    public Optional<Users> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserDetails loadUserById(Long id) {
        Optional<Users> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new NullPointerException();
        }
        return UserPrinciple.build(user.get());
    }

    @Override
    public boolean checkLogin(Users user) {
        Iterable<Users> users = this.findAll();
        boolean isCorrectUser = false;
        for (Users currentUser : users) {
            if (currentUser.getEmail().equals(user.getEmail())
                    && user.getPassword().equals(currentUser.getPassword())&&
                    currentUser.isEnabled()) {
                isCorrectUser = true;
            }
        }
        return isCorrectUser;
    }

    @Override
    public boolean isRegister(Users user) {
        boolean isRegister = false;
        Iterable<Users> users = this.findAll();
        for (Users currentUser : users) {
            if (user.getEmail().equals(currentUser.getEmail())) {
                isRegister = true;
                break;
            }
        }
        return isRegister;
    }

//    @Override
//    public boolean isCorrectConfirmPassword(User user) {
//        boolean isCorrentConfirmPassword = false;
//        if(user.getPassword().equals(user.getConfirmPassword())){
//            isCorrentConfirmPassword = true;
//        }
//        return isCorrentConfirmPassword;
//    }
}
