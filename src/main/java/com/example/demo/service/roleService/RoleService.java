package com.example.demo.service.roleService;

import com.example.demo.model.Role;
import com.example.demo.repository.IRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService implements IRoleService{
    @Autowired
    private IRoleRepo iRoleRepo;
    @Override
    public Iterable<Role> findAll() {
        return iRoleRepo.findAll();
    }

    @Override
    public Optional<Role> findById(Long id) {
        return iRoleRepo.findById(id);
    }

    @Override
    public void save(Role role) {
        iRoleRepo.save(role);
    }

    @Override
    public void remove(Long id) {
        iRoleRepo.deleteById(id);
    }
}
