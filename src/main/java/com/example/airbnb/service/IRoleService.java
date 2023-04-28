package com.example.airbnb.service;


import com.example.airbnb.model.Role;


public interface IRoleService extends IGeneralService<Role> {
    Role findByName(String name);
}
