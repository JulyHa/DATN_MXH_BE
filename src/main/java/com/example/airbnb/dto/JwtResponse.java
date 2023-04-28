package com.example.airbnb.dto;

import com.example.airbnb.model.Users;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

@Getter
@Setter
public class JwtResponse {
    private Long id;
    private String token;
    private String type = "Bearer ";
    private Users users;

    private Collection<? extends GrantedAuthority> roles;
    public JwtResponse(String accessToken, Long id, Users users,  Collection<? extends GrantedAuthority> roles) {
        this.token = type + accessToken;
        this.users = users;
        this.roles = roles;
        this.id = id;
    }
}
