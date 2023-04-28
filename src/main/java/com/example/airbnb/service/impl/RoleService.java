package com.example.airbnb.service.impl;

import com.example.airbnb.model.Role;
import com.example.airbnb.repository.IRoleRepository;
import com.example.airbnb.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public Iterable<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public void save(Role role) {
roleRepository.save(role);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }


}
