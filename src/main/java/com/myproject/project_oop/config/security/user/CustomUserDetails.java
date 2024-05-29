package com.myproject.project_oop.config.security.user;

import com.myproject.project_oop.model.User;
import com.myproject.project_oop.model.constant.Status;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Builder
public class CustomUserDetails implements UserDetails {

    private String username;

    private String password;

    private Status status;

    public static CustomUserDetails buildFromUser(User user) {
        return CustomUserDetails.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .status(user.getStatus())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(status.getAuthority());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
