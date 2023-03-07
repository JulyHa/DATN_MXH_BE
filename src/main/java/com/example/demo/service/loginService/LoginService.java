package com.example.demo.service.loginService;

import com.example.demo.dao.UserUpdate;
import com.example.demo.model.Users;
import com.example.demo.repository.IRoleRepo;
import com.example.demo.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LoginService implements ILoginService {
    @Autowired
    private IUserRepo userRepo;
    @Autowired
    private IRoleRepo roleRepo;

    public Users login(Users user) {
        List<Users> users = userRepo.findAll();
        for (Users u : users) {
            if (u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())) {
                u.setCheckOn(true);
                return u;
            }
        }
        return null;
    }

    public boolean changePassword(UserUpdate userUpdate){
        Users user_update =userRepo.findById(userUpdate.getId()).get();
        if(!userUpdate.getOldPassword().equals(userUpdate.getNewPassword())){
            user_update.setPassword(userUpdate.getNewPassword());
            userRepo.save(user_update);
            return true;
        }
        return false;
    }

}
