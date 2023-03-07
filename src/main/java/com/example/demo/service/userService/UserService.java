package com.example.demo.service.userService;

import com.example.demo.model.Users;
import com.example.demo.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserService implements IUserService{
    @Autowired
    private IUserRepo iUserRepo;
    @Override
    public Iterable<Users> findAll() {
        return iUserRepo.findAll();
    }

    @Override
    public Optional<Users> findById(Long id) {
        return iUserRepo.findById(id);
    }

    @Override
    public void save(Users users) {
        iUserRepo.save(users);
    }
    @Override
    public Users update(Users users, Long id){
        Optional<Users> usersOptional = iUserRepo.findById(id);
        if(usersOptional.isEmpty()){
            return null;
        }
        users.setId(id);
        if (users.getAvatar() == null) {
            users.setAvatar(usersOptional.get().getAvatar());
        }
        users.setPassword(usersOptional.get().getPassword());
        users.setStatus(usersOptional.get().isStatus());
        users.setRole(usersOptional.get().getRole());
        iUserRepo.save(users);
        return users;
    }

    @Override
    public Users findUserByUsername(String username) {
        Users user = iUserRepo.findUsersByUsername(username).get();
        user.setCheckOn(false);
        iUserRepo.save(user);
        return user;
    }

    public Users setSeeComment(Users user){
        Users u = iUserRepo.findUsersByUsername(user.getUsername()).get();
        u.setSeeFriendPermission(user.isSeeFriendPermission());
        u.setCommentPermission(user.isCommentPermission());
        iUserRepo.save(u);
        return u;
    }

    @Override
    public void remove(Long id) {
        iUserRepo.deleteById(id);
    }

    @Override
    public Iterable<Users> findFriendRequestsByIdAndStatusTrue(Long id) {
        return iUserRepo.findFriendRequestsByIdAndStatusTrue(id);
    }
}
