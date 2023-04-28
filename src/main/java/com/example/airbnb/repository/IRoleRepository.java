package com.example.airbnb.repository;


import com.example.airbnb.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String roleName);
}