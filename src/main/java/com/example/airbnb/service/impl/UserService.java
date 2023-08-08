package com.example.airbnb.service.impl;

import com.example.airbnb.dto.UserUpdate;
import com.example.airbnb.model.Users;
import com.example.airbnb.dto.UserPrinciple;
import com.example.airbnb.repository.IUserRepository;
import com.example.airbnb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        Users user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        if (this.checkLogin(user)) {
            user.setCheckOn(true);
            userRepository.save(user);
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
    public boolean checkUser(Users user) {
        Iterable<Users> users = this.findAll();
        boolean isCorrectUser = false;
        for (Users currentUser : users) {
            boolean res = currentUser.getEmail().equals(user.getEmail())
                    && passwordEncoder.matches(user.getPassword(), currentUser.getPassword());
            if (res) {
                isCorrectUser = true;
                break;
            }
        }
        return isCorrectUser;
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
                break;
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
    @Override
    public boolean changePassword(UserUpdate userUpdate) {
        Users users_update = userRepository.findById(userUpdate.getId()).get();
        boolean res = checkUser(new Users(users_update.getEmail(), userUpdate.getOldPassword()));
        if(res){
            users_update.setPassword(passwordEncoder.encode(userUpdate.getNewPassword()));
            userRepository.save(users_update);
        }
        return res;
    }

    @Override
    public List<Users> findFriendRequestsByIdAndStatusTrue(Long id) {
        return userRepository.findFriendRequestsByIdAndStatusTrue(id);
    }

    @Override
    public List<Users> findUsersActiveByName(String name) {
        return userRepository.findUsersByFirstNameContainingOrLastNameContainingAndEnabledIsTrue(name, name);
    }

    @Override
    public List<Users> listFriendRequest(Long id) {
        return userRepository.listFriendRequest(id);
    }

    @Override
    public List<Users> findAllLikePost(Long id) {
        return userRepository.findAllLikePost(id);
    }

    @Override
    public List<Users> findInListFriend(Long id, String q) {
        List<Users> users = findFriendRequestsByIdAndStatusTrue(id);
        List<Users> listSearch = new ArrayList<>();
        for (Users users1: users){
            if (users1.getFirstName().contains(q) || users1.getLastName().contains(q)){
                listSearch.add(users1);
            }
        }
        return listSearch;
    }

    @Override
    public List<Users> findMemberByConversation(Long id) {
        return userRepository.findMemberByConversation(id);
    }
}
