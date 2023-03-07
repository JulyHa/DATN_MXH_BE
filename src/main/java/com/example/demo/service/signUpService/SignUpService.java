package com.example.demo.service.signUpService;

import com.example.demo.model.Users;
import com.example.demo.repository.IUserRepo;
import com.example.demo.service.roleService.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SignUpService implements ISignUpService {
    @Autowired
    private IUserRepo iUserRepo;
    @Autowired
    private IRoleService iRoleService;

    @Override
    public boolean signUp(Users users) {
        if (!checkUserExist(users)) {
            users.setRole(iRoleService.findById(2L).get());
            users.setAvatar("https://i0.wp.com/sbcf.fr/wp-content/uploads/2018/03/sbcf-default-avatar.png?ssl=1");
            users.setStatus(true);
            users.setCheckOn(false);
            users.setCommentPermission(true);
            users.setSeeFriendPermission(true);
            iUserRepo.save(users);
            return true;
        }
        return false;
    }

    @Override
    public boolean checkUserExist(Users users) {
        List<Users> usersList = iUserRepo.findAll();
        for (Users u : usersList) {
            if (users.getUsername().equals(u.getUsername()) || users.getEmail().equals(u.getEmail())) {
                return true;
            }
        }
        return false;
    }
}
